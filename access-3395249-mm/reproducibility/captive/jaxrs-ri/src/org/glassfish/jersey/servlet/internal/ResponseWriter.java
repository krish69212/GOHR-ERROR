/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.servlet.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import jakarta.servlet.http.HttpServletResponse;

import org.glassfish.jersey.server.ContainerException;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.JerseyRequestTimeoutHandler;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;
import org.glassfish.jersey.servlet.spi.AsyncContextDelegate;

/**
 * An internal implementation of {@link ContainerResponseWriter} for Servlet containers.
 * The writer depends on provided {@link AsyncContextDelegate} to support async functionality.
 *
 * @author Paul Sandoz
 * @author Jakub Podlesak
 * @author Martin Matula
 * @author Libor Kramolis
 */
public class ResponseWriter implements ContainerResponseWriter {

    private static final Logger LOGGER = Logger.getLogger(ResponseWriter.class.getName());

    private final HttpServletResponse response;
    /**
     * Cached value of configuration property
     * {@link org.glassfish.jersey.servlet.ServletProperties#FILTER_FORWARD_ON_404}.
     */
    private final boolean useSetStatusOn404;
    /**
     * Cached value of configuration property
     * {@link org.glassfish.jersey.server.ServerProperties#RESPONSE_SET_STATUS_OVER_SEND_ERROR}.
     * If {@code true} method {@link HttpServletResponse#setStatus} is used over {@link HttpServletResponse#sendError}.
     */
    private final boolean configSetStatusOverSendError;
    private final CompletableFuture<ContainerResponse> responseContext;
    private final AsyncContextDelegate asyncExt;

    private final JerseyRequestTimeoutHandler requestTimeoutHandler;

    /**
     * Creates a new instance to write a single Jersey response.
     *
     * @param useSetStatusOn404            true if status should be written explicitly when 404 is returned
     * @param configSetStatusOverSendError if {@code true} method {@link HttpServletResponse#setStatus} is used over
     *                                     {@link HttpServletResponse#sendError}
     * @param response                     original HttpResponseRequest
     * @param asyncExt                     delegate to use for async features implementation
     * @param timeoutTaskExecutor          Jersey runtime executor used for background execution of timeout
     *                                     handling tasks.
     */
    public ResponseWriter(final boolean useSetStatusOn404,
                          final boolean configSetStatusOverSendError,
                          final HttpServletResponse response,
                          final AsyncContextDelegate asyncExt,
                          final ScheduledExecutorService timeoutTaskExecutor) {
        this.useSetStatusOn404 = useSetStatusOn404;
        this.configSetStatusOverSendError = configSetStatusOverSendError;
        this.response = response;
        this.asyncExt = asyncExt;
        this.responseContext = new CompletableFuture<>();

        this.requestTimeoutHandler = new JerseyRequestTimeoutHandler(this, timeoutTaskExecutor);
    }

    @Override
    public boolean suspend(final long timeOut, final TimeUnit timeUnit, final TimeoutHandler timeoutHandler) {
        try {
            // Suspend the servlet.
            asyncExt.suspend();
        } catch (final IllegalStateException ex) {
            LOGGER.log(Level.WARNING, LocalizationMessages.SERVLET_REQUEST_SUSPEND_FAILED(), ex);
            return false;
        }
        // Suspend the internal request timeout handler.
        return requestTimeoutHandler.suspend(timeOut, timeUnit, timeoutHandler);
    }

    @Override
    public void setSuspendTimeout(final long timeOut, final TimeUnit timeUnit) throws IllegalStateException {
        requestTimeoutHandler.setSuspendTimeout(timeOut, timeUnit);
    }

    @Override
    public OutputStream writeResponseStatusAndHeaders(final long contentLength, final ContainerResponse responseContext)
            throws ContainerException {
        this.responseContext.complete(responseContext);

        // first set the content length, so that if headers have an explicit value, it takes precedence over this one
        if (responseContext.hasEntity() && contentLength != -1 && contentLength < Integer.MAX_VALUE) {
            response.setContentLength((int) contentLength);
        }
        // Note that the writing of headers MUST be performed before
        // the invocation of sendError as on some Servlet implementations
        // modification of the response headers will have no effect
        // after the invocation of sendError.
        final MultivaluedMap<String, String> headers = getResponseContext().getStringHeaders();
        for (final Map.Entry<String, List<String>> e : headers.entrySet()) {
            final Iterator<String> it = e.getValue().iterator();
            if (!it.hasNext()) {
                continue;
            }
            final String header = e.getKey();
            if (response.containsHeader(header)) {
                // replace any headers previously set with values from Jersey container response.
                response.setHeader(header, it.next());
            }

            while (it.hasNext()) {
                response.addHeader(header, it.next());
            }
        }

        final String reasonPhrase = responseContext.getStatusInfo().getReasonPhrase();
        if (reasonPhrase != null) {
            response.setStatus(responseContext.getStatus(), reasonPhrase);
        } else {
            response.setStatus(responseContext.getStatus());
        }

        if (!responseContext.hasEntity()) {
            return null;
        } else {
            try {
                final OutputStream outputStream = response.getOutputStream();

                // delegating output stream prevents closing the underlying servlet output stream,
                // so that any Servlet filters in the chain can still write to the response after us.
                return new NonCloseableOutputStreamWrapper(outputStream);
            } catch (final IOException e) {
                throw new ContainerException(e);
            }
        }
    }

    @Override
    public void commit() {
        try {
            callSendError();
        } finally {
            requestTimeoutHandler.close();
            asyncExt.complete();
        }
    }

    /**
     * According to configuration and response processing status it calls {@link HttpServletResponse#sendError(int, String)} over
     * common {@link HttpServletResponse#setStatus(int)}.
     */
    private void callSendError() {
        // call HttpServletResponse.sendError in case:
        // - property ServerProperties#RESPONSE_SET_STATUS_OVER_SEND_ERROR == false (default)
        // - response NOT yet committed
        // - response entity NOT set
        // - response status code is 4xx or 5xx
        // plus in case of Jersey as a Filter (JaaF):
        // - response status code is 404 (Not Found)
        // - property ServletProperties#FILTER_FORWARD_ON_404 == false (default)
        if (!configSetStatusOverSendError && !response.isCommitted()) {
            final ContainerResponse responseContext = getResponseContext();
            final boolean hasEntity = responseContext.hasEntity();
            final Response.StatusType status = responseContext.getStatusInfo();
            if (!hasEntity && status != null && status.getStatusCode() >= 400
                && !(useSetStatusOn404 && status == Response.Status.NOT_FOUND)) {
                final String reason = status.getReasonPhrase();
                try {
                    if (reason == null || reason.isEmpty()) {
                        response.sendError(status.getStatusCode());
                    } else {
                        response.sendError(status.getStatusCode(), reason);
                    }
                } catch (final IOException ex) {
                    throw new ContainerException(
                            LocalizationMessages.EXCEPTION_SENDING_ERROR_RESPONSE(status, reason != null ? reason : "--"),
                            ex);
                }
            }
        }
    }

    @Override
    public void failure(final Throwable error) {
        try {
            if (!response.isCommitted()) {
                try {
                    if (configSetStatusOverSendError) {
                        response.reset();
                        //noinspection deprecation
                        response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Request failed.");
                    } else {
                        response.sendError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Request failed.");
                    }
                } catch (final IllegalStateException ex) {
                    // a race condition externally committing the response can still occur...
                    LOGGER.log(Level.FINER, "Unable to reset failed response.", ex);
                } catch (final IOException ex) {
                    throw new ContainerException(LocalizationMessages.EXCEPTION_SENDING_ERROR_RESPONSE(
                            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Request failed."), ex);
                } finally {
                    asyncExt.complete();
                }
            }
        } finally {
            requestTimeoutHandler.close();
            rethrow(error);
        }
    }

    @Override
    public boolean enableResponseBuffering() {
        return true;
    }

    /**
     * Rethrow the original exception as required by JAX-RS, 3.3.4
     *
     * @param error throwable to be re-thrown
     */
    private void rethrow(final Throwable error) {
        if (error instanceof RuntimeException) {
            throw (RuntimeException) error;
        } else {
            throw new ContainerException(error);
        }
    }

    /**
     * Provides response status captured when
     * {@link #writeResponseStatusAndHeaders(long, org.glassfish.jersey.server.ContainerResponse)} has been invoked.
     * The method will block if the write method has not been called yet.
     *
     * @return response status
     */
    public int getResponseStatus() {
        return getResponseContext().getStatus();
    }

    public boolean responseContextResolved() {
        return responseContext.isDone();
    }

    public ContainerResponse getResponseContext() {
        try {
            return responseContext.get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new ContainerException(ex);
        }
    }

    private static class NonCloseableOutputStreamWrapper extends OutputStream {

        private final OutputStream delegate;

        public NonCloseableOutputStreamWrapper(final OutputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public void write(final int b) throws IOException {
            delegate.write(b);
        }

        @Override
        public void write(final byte[] b) throws IOException {
            delegate.write(b);
        }

        @Override
        public void write(final byte[] b, final int off, final int len) throws IOException {
            delegate.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            delegate.flush();
        }

        @Override
        public void close() throws IOException {
            // do not close - let the servlet container close the stream
        }
    }
}
