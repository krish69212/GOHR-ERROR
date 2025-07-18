/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.jersey.client;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Variant;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.WriterInterceptor;

import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerSupplier;
import org.glassfish.jersey.internal.util.ExceptionUtils;
import org.glassfish.jersey.internal.PropertiesResolver;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.internal.HeaderUtils;
import org.glassfish.jersey.message.internal.OutboundMessageContext;

/**
 * Jersey client request context.
 *
 * @author Marek Potociar
 */
public class ClientRequest extends OutboundMessageContext
        implements ClientRequestContext, HttpHeaders, InjectionManagerSupplier, PropertiesResolver {

    // Request-scoped configuration instance
    private final ClientConfig clientConfig;
    // Request-scoped properties delegate
    private final PropertiesDelegate propertiesDelegate;
    // Absolute request URI
    private URI requestUri;
    // Request method
    private String httpMethod;
    // Request filter chain execution aborting response
    private Response abortResponse;
    // Entity providers
    private MessageBodyWorkers workers;
    // Flag indicating whether the request is asynchronous
    private boolean asynchronous;
    // true if writeEntity() was already called
    private boolean entityWritten;
    // writer interceptors used to write the request
    private Iterable<WriterInterceptor> writerInterceptors;
    // reader interceptors used to write the request
    private Iterable<ReaderInterceptor> readerInterceptors;
    // do not add user-agent header (if not directly set) to the request.
    private boolean ignoreUserAgent;
    // lazy PropertiesResolver
    private  LazyValue<PropertiesResolver> propertiesResolver = Values.lazy(
            (Value<PropertiesResolver>) () -> PropertiesResolver.create(getConfiguration(), getPropertiesDelegate())
    );

    private static final Logger LOGGER = Logger.getLogger(ClientRequest.class.getName());

    /**
     * Create new Jersey client request context.
     *
     * @param requestUri         request Uri.
     * @param clientConfig      request configuration.
     * @param propertiesDelegate properties delegate.
     */
    protected ClientRequest(
            final URI requestUri, final ClientConfig clientConfig, final PropertiesDelegate propertiesDelegate) {
        super(clientConfig.getConfiguration());
        clientConfig.checkClient();

        this.requestUri = requestUri;
        this.clientConfig = clientConfig;
        this.propertiesDelegate = propertiesDelegate;
    }

    /**
     * Copy constructor.
     *
     * @param original original instance.
     */
    public ClientRequest(final ClientRequest original) {
        super(original);
        this.requestUri = original.requestUri;
        this.httpMethod = original.httpMethod;
        this.workers = original.workers;
        this.clientConfig = original.clientConfig.snapshot();
        this.asynchronous = original.isAsynchronous();
        this.readerInterceptors = original.readerInterceptors;
        this.writerInterceptors = original.writerInterceptors;
        this.propertiesDelegate = new MapPropertiesDelegate(original.propertiesDelegate);
        this.ignoreUserAgent = original.ignoreUserAgent;
    }

    @Override
    public <T> T resolveProperty(final String name, final Class<T> type) {
        return propertiesResolver.get().resolveProperty(name, type);
    }

    @Override
    public <T> T resolveProperty(final String name, final T defaultValue) {
        return propertiesResolver.get().resolveProperty(name, defaultValue);
    }

    @Override
    public Object getProperty(final String name) {
        return propertiesDelegate.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return propertiesDelegate.getPropertyNames();
    }

    @Override
    public void setProperty(final String name, final Object object) {
        propertiesDelegate.setProperty(name, object);
    }

    @Override
    public void removeProperty(final String name) {
        propertiesDelegate.removeProperty(name);
    }

    /**
     * Get the underlying properties delegate.
     *
     * @return underlying properties delegate.
     */
    PropertiesDelegate getPropertiesDelegate() {
        return propertiesDelegate;
    }

    /**
     * Get the underlying client runtime.
     *
     * @return underlying client runtime.
     */
    ClientRuntime getClientRuntime() {
        return clientConfig.getRuntime();
    }

    @Override
    public URI getUri() {
        return requestUri;
    }

    @Override
    public void setUri(final URI uri) {
        this.requestUri = uri;
    }

    @Override
    public String getMethod() {
        return httpMethod;
    }

    @Override
    public void setMethod(final String method) {
        this.httpMethod = method;
    }

    @Override
    public JerseyClient getClient() {
        return clientConfig.getClient();
    }

    @Override
    public void abortWith(final Response response) {
        this.abortResponse = response;
    }

    /**
     * Get the request filter chain aborting response if set, or {@code null} otherwise.
     *
     * @return request filter chain aborting response if set, or {@code null} otherwise.
     */
    public Response getAbortResponse() {
        return abortResponse;
    }

    @Override
    public Configuration getConfiguration() {
        return clientConfig.getRuntime().getConfig();
    }

    /**
     * Get internal client configuration state.
     *
     * @return internal client configuration state.
     */
    ClientConfig getClientConfig() {
        return clientConfig;
    }

    /**
     * Get the values of an HTTP request header if the header exists on the current request. The returned value will be
     * a read-only List if the specified header exists or {@code null} if it does not. This is a shortcut for
     * {@code getRequestHeaders().get(name)}.
     *
     * @param name the header name, case insensitive.
     * @return a read-only list of header values if the specified header exists, otherwise {@code null}.
     * @throws java.lang.IllegalStateException if called outside the scope of a request.
     */
    @Override
    public List<String> getRequestHeader(String name) {
        final List<Object> values = getHeaders().get(name);
        return values == null ? null : HeaderUtils.asStringList(values, clientConfig.getConfiguration());
    }

    @Override
    public MultivaluedMap<String, String> getRequestHeaders() {
        return HeaderUtils.asStringHeaders(getHeaders(), clientConfig.getConfiguration());
    }

    @Override
    public Map<String, Cookie> getCookies() {
        return super.getRequestCookies();
    }

    /**
     * Get the message body workers associated with the request.
     *
     * @return message body workers.
     */
    public MessageBodyWorkers getWorkers() {
        return workers;
    }

    /**
     * Set the message body workers associated with the request.
     *
     * @param workers message body workers.
     */
    public void setWorkers(final MessageBodyWorkers workers) {
        this.workers = workers;
    }

    /**
     * Add new accepted types to the message headers.
     *
     * @param types accepted types to be added.
     */
    public void accept(final MediaType... types) {
        getHeaders().addAll(HttpHeaders.ACCEPT, (Object[]) types);
    }

    /**
     * Add new accepted types to the message headers.
     *
     * @param types accepted types to be added.
     */
    public void accept(final String... types) {
        getHeaders().addAll(HttpHeaders.ACCEPT, (Object[]) types);
    }

    /**
     * Add new accepted languages to the message headers.
     *
     * @param locales accepted languages to be added.
     */
    public void acceptLanguage(final Locale... locales) {
        getHeaders().addAll(HttpHeaders.ACCEPT_LANGUAGE, (Object[]) locales);
    }

    /**
     * Add new accepted languages to the message headers.
     *
     * @param locales accepted languages to be added.
     */
    public void acceptLanguage(final String... locales) {
        getHeaders().addAll(HttpHeaders.ACCEPT_LANGUAGE, (Object[]) locales);
    }

    /**
     * Add new cookie to the message headers.
     *
     * @param cookie cookie to be added.
     */
    public void cookie(final Cookie cookie) {
        getHeaders().add(HttpHeaders.COOKIE, cookie);
    }

    /**
     * Add new cache control entry to the message headers.
     *
     * @param cacheControl cache control entry to be added.
     */
    public void cacheControl(final CacheControl cacheControl) {
        getHeaders().add(HttpHeaders.CACHE_CONTROL, cacheControl);
    }

    /**
     * Set message encoding.
     *
     * @param encoding message encoding to be set.
     */
    public void encoding(final String encoding) {
        if (encoding == null) {
            getHeaders().remove(HttpHeaders.CONTENT_ENCODING);
        } else {
            getHeaders().putSingle(HttpHeaders.CONTENT_ENCODING, encoding);
        }
    }

    /**
     * Set message language.
     *
     * @param language message language to be set.
     */
    public void language(final String language) {
        if (language == null) {
            getHeaders().remove(HttpHeaders.CONTENT_LANGUAGE);
        } else {
            getHeaders().putSingle(HttpHeaders.CONTENT_LANGUAGE, language);
        }
    }

    /**
     * Set message language.
     *
     * @param language message language to be set.
     */
    public void language(final Locale language) {
        if (language == null) {
            getHeaders().remove(HttpHeaders.CONTENT_LANGUAGE);
        } else {
            getHeaders().putSingle(HttpHeaders.CONTENT_LANGUAGE, language);
        }
    }

    /**
     * Set message content type.
     *
     * @param type message content type to be set.
     */
    public void type(final MediaType type) {
        setMediaType(type);
    }

    /**
     * Set message content type.
     *
     * @param type message content type to be set.
     */
    public void type(final String type) {
        type(type == null ? null : MediaType.valueOf(type));
    }

    /**
     * Set message content variant (type, language and encoding).
     *
     * @param variant message content content variant (type, language and encoding)
     *                to be set.
     */
    public void variant(final Variant variant) {
        if (variant == null) {
            type((MediaType) null);
            language((String) null);
            encoding(null);
        } else {
            type(variant.getMediaType());
            language(variant.getLanguage());
            encoding(variant.getEncoding());
        }
    }

    /**
     * Returns true if the request is called asynchronously using {@link jakarta.ws.rs.client.AsyncInvoker}
     *
     * @return True if the request is asynchronous; false otherwise.
     */
    public boolean isAsynchronous() {
        return asynchronous;
    }

    /**
     * Sets the flag indicating whether the request is called asynchronously using {@link jakarta.ws.rs.client.AsyncInvoker}.
     *
     * @param async True if the request is asynchronous; false otherwise.
     */
    void setAsynchronous(final boolean async) {
        asynchronous = async;
    }

    /**
     * Enable a buffering of serialized entity. The buffering will be configured from runtime configuration
     * associated with this request. The property determining the size of the buffer
     * is {@link org.glassfish.jersey.CommonProperties#OUTBOUND_CONTENT_LENGTH_BUFFER}.
     * <p/>
     * The buffering functionality is by default disabled and could be enabled by calling this method. In this case
     * this method must be called before first bytes are written to the {@link #getEntityStream() entity stream}.
     *
     */
    public void enableBuffering() {
        enableBuffering(getConfiguration());
    }

    /**
     * Write (serialize) the entity set in this request into the {@link #getEntityStream() entity stream}. The method
     * use {@link jakarta.ws.rs.ext.WriterInterceptor writer interceptors} and {@link jakarta.ws.rs.ext.MessageBodyWriter
     * message body writer}.
     * <p/>
     * This method modifies the state of this request and therefore it can be called only once per request life cycle otherwise
     * IllegalStateException is thrown.
     * <p/>
     * Note that {@link #setStreamProvider(org.glassfish.jersey.message.internal.OutboundMessageContext.StreamProvider)}
     * and optionally {@link #enableBuffering()} must be called before calling this method.
     *
     * @throws IOException In the case of IO error.
     */
    public void writeEntity() throws IOException {
        Preconditions.checkState(!entityWritten, LocalizationMessages.REQUEST_ENTITY_ALREADY_WRITTEN());
        entityWritten = true;
        ensureMediaType();
        final GenericType<?> entityType = new GenericType(getEntityType());
        doWriteEntity(workers, entityType);
    }

    /**
     * Added only to make the code testable.
     *
     * @param writeWorkers Message body workers instance used to write the entity.
     * @param entityType   entity type.
     * @throws IOException when {@link MessageBodyWorkers#writeTo(Object, Class, Type, Annotation[], MediaType,
     *                     MultivaluedMap, PropertiesDelegate, OutputStream, Iterable)} throws an {@link IOException}.
     *                     This state is always regarded as connection failure.
     */
    /* package */ void doWriteEntity(final MessageBodyWorkers writeWorkers, final GenericType<?> entityType) throws IOException {
        OutputStream entityStream = null;
        boolean connectionFailed = false;
        boolean runtimeException = false;
        try {
            try {
                entityStream = writeWorkers.writeTo(
                        getEntity(),
                        entityType.getRawType(),
                        entityType.getType(),
                        getEntityAnnotations(),
                        getMediaType(),
                        getHeaders(),
                        getPropertiesDelegate(),
                        getEntityStream(),
                        writerInterceptors);
                setEntityStream(entityStream);
            } catch (final IOException e) {
                // JERSEY-2728 - treat SSLException as connection failure
                connectionFailed = true;
                throw e;
            } catch (final RuntimeException e) {
                runtimeException = true;
                throw e;
            }
        } finally {
            // in case we've seen the ConnectException, we won't try to close/commit stream as this would produce just
            // another instance of ConnectException (which would be logged even if the previously thrown one is propagated)
            // However, if another failure occurred, we still have to try to close and commit the stream - and if we experience
            // another failure, there is a valid reason to log it
            if (!connectionFailed) {
                if (entityStream != null) {
                    try {
                        entityStream.close();
                    } catch (final IOException e) {
                        ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER,
                                LocalizationMessages.ERROR_CLOSING_OUTPUT_STREAM(), Level.FINE);
                    } catch (final RuntimeException e) {
                        ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER,
                                LocalizationMessages.ERROR_CLOSING_OUTPUT_STREAM(), Level.FINE);
                    }
                }
                try {
                    commitStream();
                } catch (final IOException e) {
                    ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER,
                            LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), Level.FINE);
                } catch (final RuntimeException e) {
                    ExceptionUtils.conditionallyReThrow(e, !runtimeException, LOGGER,
                            LocalizationMessages.ERROR_COMMITTING_OUTPUT_STREAM(), Level.FINE);
                }
            }
        }
    }

    private void ensureMediaType() {
        if (getMediaType() == null) {
            // Content-Type is not present choose a default type
            final GenericType<?> entityType = new GenericType(getEntityType());
            final List<MediaType> mediaTypes = workers.getMessageBodyWriterMediaTypes(
                    entityType.getRawType(), entityType.getType(), getEntityAnnotations());

            setMediaType(getMediaType(mediaTypes));
        }
    }

    private MediaType getMediaType(final List<MediaType> mediaTypes) {
        if (mediaTypes.isEmpty()) {
            return MediaType.APPLICATION_OCTET_STREAM_TYPE;
        } else {
            MediaType mediaType = mediaTypes.get(0);
            if (mediaType.isWildcardType() || mediaType.isWildcardSubtype()) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM_TYPE;
            }
            return mediaType;
        }
    }

    /**
     * Set writer interceptors for this request.
     * @param writerInterceptors Writer interceptors in the interceptor execution order.
     */
    void setWriterInterceptors(final Iterable<WriterInterceptor> writerInterceptors) {
        this.writerInterceptors = writerInterceptors;
    }

    /**
     * Get writer interceptors of this request.
     * @return Writer interceptors in the interceptor execution order.
     */
    public Iterable<WriterInterceptor> getWriterInterceptors() {
        return writerInterceptors;
    }

    /**
     * Get reader interceptors of this request.
     * @return Reader interceptors in the interceptor execution order.
     */
    public Iterable<ReaderInterceptor> getReaderInterceptors() {
        return readerInterceptors;
    }

    /**
     * Set reader interceptors for this request.
     * @param readerInterceptors Reader interceptors in the interceptor execution order.
     */
    void setReaderInterceptors(final Iterable<ReaderInterceptor> readerInterceptors) {
        this.readerInterceptors = readerInterceptors;
    }

    @Override
    public InjectionManager getInjectionManager() {
        return getClientRuntime().getInjectionManager();
    }

    /**
     * Indicates whether the User-Agent header should be omitted if not directly set to the map of headers.
     *
     * @return {@code true} if the header should be omitted, {@code false} otherwise.
     */
    public boolean ignoreUserAgent() {
        return ignoreUserAgent;
    }

    /**
     * Indicates whether the User-Agent header should be omitted if not directly set to the map of headers.
     *
     * @param ignore {@code true} if the header should be omitted, {@code false} otherwise.
     */
    public void ignoreUserAgent(final boolean ignore) {
        this.ignoreUserAgent = ignore;
    }
}
