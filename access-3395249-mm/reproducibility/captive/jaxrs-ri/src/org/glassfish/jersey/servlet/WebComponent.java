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

package org.glassfish.jersey.servlet;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jakarta.ws.rs.RuntimeType;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.glassfish.jersey.internal.ServiceFinderBinder;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.internal.inject.ReferencingFactory;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.message.internal.HeaderValueException;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.BackgroundSchedulerLiteral;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.internal.InternalServerProperties;
import org.glassfish.jersey.server.spi.RequestScopedInitializer;
import org.glassfish.jersey.servlet.internal.LocalizationMessages;
import org.glassfish.jersey.servlet.internal.PersistenceUnitBinder;
import org.glassfish.jersey.servlet.internal.ResponseWriter;
import org.glassfish.jersey.servlet.internal.ServletContainerProviderFactory;
import org.glassfish.jersey.servlet.internal.Utils;
import org.glassfish.jersey.servlet.internal.spi.ExtendedServletContainerProvider;
import org.glassfish.jersey.servlet.internal.spi.RequestContextProvider;
import org.glassfish.jersey.servlet.internal.spi.RequestScopedInitializerProvider;
import org.glassfish.jersey.servlet.internal.spi.ServletContainerProvider;
import org.glassfish.jersey.servlet.spi.AsyncContextDelegate;
import org.glassfish.jersey.servlet.spi.AsyncContextDelegateProvider;
import org.glassfish.jersey.servlet.spi.FilterUrlMappingsProvider;
import org.glassfish.jersey.uri.UriComponent;

/**
 * An common Jersey web component that may be extended by a Servlet and/or
 * Filter implementation, or encapsulated by a Servlet or Filter implementation.
 *
 * @author Paul Sandoz
 * @author Jakub Podlesak
 * @author Marek Potociar
 * @author Martin Matula
 * @author Libor Kramolis
 */
public class WebComponent {

    private static final Logger LOGGER = Logger.getLogger(WebComponent.class.getName());

    private static final Type REQUEST_TYPE = (new GenericType<Ref<HttpServletRequest>>() {}).getType();
    private static final Type RESPONSE_TYPE = (new GenericType<Ref<HttpServletResponse>>() {}).getType();

    private static final AsyncContextDelegate DEFAULT_ASYNC_DELEGATE = new AsyncContextDelegate() {

        @Override
        public void suspend() throws IllegalStateException {
            throw new UnsupportedOperationException(LocalizationMessages.ASYNC_PROCESSING_NOT_SUPPORTED());
        }

        @Override
        public void complete() {
        }
    };

    private final RequestScopedInitializerProvider requestScopedInitializer;
    private final boolean requestResponseBindingExternalized;

    private static final RequestScopedInitializerProvider DEFAULT_REQUEST_SCOPE_INITIALIZER_PROVIDER =
            context -> (RequestScopedInitializer) injectionManager -> {
                injectionManager.<Ref<HttpServletRequest>>getInstance(REQUEST_TYPE).set(context.getHttpServletRequest());
                injectionManager.<Ref<HttpServletResponse>>getInstance(RESPONSE_TYPE).set(context.getHttpServletResponse());
            };

    /**
     * Return the first found {@link AsyncContextDelegateProvider}
     * (via {@link Providers#getAllProviders(InjectionManager, Class)}) or {@code #DEFAULT_ASYNC_DELEGATE} if
     * other delegate cannot be found.
     *
     * @return a non-null AsyncContextDelegateProvider.
     */
    private AsyncContextDelegateProvider getAsyncExtensionDelegate() {
        final Iterator<AsyncContextDelegateProvider> providers = Providers.getAllProviders(appHandler.getInjectionManager(),
                AsyncContextDelegateProvider.class).iterator();
        if (providers.hasNext()) {
            return providers.next();
        }

        return (request, response) -> DEFAULT_ASYNC_DELEGATE;
    }

    @SuppressWarnings("JavaDoc")
    private static class HttpServletRequestReferencingFactory extends ReferencingFactory<HttpServletRequest> {

        @Inject
        public HttpServletRequestReferencingFactory(final Provider<Ref<HttpServletRequest>> referenceFactory) {
            super(referenceFactory);
        }
    }

    @SuppressWarnings("JavaDoc")
    private static class HttpServletResponseReferencingFactory extends ReferencingFactory<HttpServletResponse> {

        @Inject
        public HttpServletResponseReferencingFactory(final Provider<Ref<HttpServletResponse>> referenceFactory) {
            super(referenceFactory);
        }
    }

    private final class WebComponentBinder extends AbstractBinder {

        private final Map<String, Object> applicationProperties;

        /**
         * Create binder for {@link WebComponent} passing a map of properties to determine whether certain features are allowed
         * or
         * not.
         *
         * @param applicationProperties map of properties to determine whether certain features are allowed or not.
         */
        private WebComponentBinder(final Map<String, Object> applicationProperties) {
            this.applicationProperties = applicationProperties;
        }

        @Override
        protected void configure() {

            if (!requestResponseBindingExternalized) {

                // request
                bindFactory(HttpServletRequestReferencingFactory.class).to(HttpServletRequest.class)
                        .proxy(true).proxyForSameScope(false).in(RequestScoped.class);

                bindFactory(ReferencingFactory.referenceFactory())
                        .to(new GenericType<Ref<HttpServletRequest>>() {}).in(RequestScoped.class);

                // response
                bindFactory(HttpServletResponseReferencingFactory.class).to(HttpServletResponse.class)
                        .proxy(true).proxyForSameScope(false).in(RequestScoped.class);
                bindFactory(ReferencingFactory.referenceFactory())
                        .to(new GenericType<Ref<HttpServletResponse>>() {}).in(RequestScoped.class);
            }

            bindFactory(webConfig::getServletContext).to(ServletContext.class).in(Singleton.class);

            final ServletConfig servletConfig = webConfig.getServletConfig();
            if (webConfig.getConfigType() == WebConfig.ConfigType.ServletConfig) {
                bindFactory(() -> servletConfig).to(ServletConfig.class).in(Singleton.class);

                // @PersistenceUnit
                final Enumeration initParams = servletConfig.getInitParameterNames();
                while (initParams.hasMoreElements()) {
                    final String initParamName = (String) initParams.nextElement();

                    if (initParamName.startsWith(PersistenceUnitBinder.PERSISTENCE_UNIT_PREFIX)) {
                        install(new PersistenceUnitBinder(servletConfig));
                        break;
                    }
                }
            } else {
                bindFactory(webConfig::getFilterConfig).to(FilterConfig.class).in(Singleton.class);
            }

            bindFactory(() -> webConfig).to(WebConfig.class).in(Singleton.class);

            install(new ServiceFinderBinder<>(AsyncContextDelegateProvider.class, applicationProperties, RuntimeType.SERVER));
            install(new ServiceFinderBinder<>(FilterUrlMappingsProvider.class, applicationProperties, RuntimeType.SERVER));
        }
    }

    /**
     * Jersey application handler.
     */
    final ApplicationHandler appHandler;

    /**
     * Jersey background task scheduler - used for scheduling request timeout event handling tasks.
     */
    final ScheduledExecutorService backgroundTaskScheduler;

    /**
     * Web component configuration.
     */
    final WebConfig webConfig;

    /**
     * If {@code true} and deployed as filter, the unmatched requests will be forwarded.
     */
    final boolean forwardOn404;

    /**
     * Cached value of configuration property
     * {@link org.glassfish.jersey.server.ServerProperties#RESPONSE_SET_STATUS_OVER_SEND_ERROR}.
     * If {@code true} method {@link HttpServletResponse#setStatus} is used over {@link HttpServletResponse#sendError}.
     */
    final boolean configSetStatusOverSendError;

    /**
     * Asynchronous context delegate provider.
     */
    private final AsyncContextDelegateProvider asyncExtensionDelegate;

    /**
     * Flag whether query parameters should be kept as entity form params if a servlet filter consumes entity and
     * Jersey has to retrieve form params from servlet request parameters.
     */
    private final boolean queryParamsAsFormParams;

    /**
     * Create and initialize new web component instance.
     *
     * @param webConfig      we component configuration.
     * @param resourceConfig Jersey application configuration.
     * @throws ServletException in case the Jersey application cannot be created from the supplied
     *                          resource configuration.
     */
    public WebComponent(final WebConfig webConfig, ResourceConfig resourceConfig) throws ServletException {

        this.webConfig = webConfig;

        if (resourceConfig == null) {
            resourceConfig = createResourceConfig(webConfig);
        }


        final ServletContainerProvider[] allServletContainerProviders =
                ServletContainerProviderFactory.getAllServletContainerProviders();

        // SPI/extension hook to configure ResourceConfig
        configure(resourceConfig, allServletContainerProviders);

        boolean rrbExternalized = false;
        RequestScopedInitializerProvider rsiProvider = null;

        for (final ServletContainerProvider servletContainerProvider : allServletContainerProviders) {
            if (servletContainerProvider instanceof ExtendedServletContainerProvider) {
                final ExtendedServletContainerProvider extendedProvider =
                        (ExtendedServletContainerProvider) servletContainerProvider;

                if (extendedProvider.bindsServletRequestResponse()) {
                    rrbExternalized = true;
                }
                if (rsiProvider == null) { // try to take the first non-null provider
                    rsiProvider = extendedProvider.getRequestScopedInitializerProvider();
                }
            }
        }

        requestScopedInitializer = rsiProvider != null ? rsiProvider : DEFAULT_REQUEST_SCOPE_INITIALIZER_PROVIDER;
        requestResponseBindingExternalized = rrbExternalized;

        final AbstractBinder webComponentBinder = new WebComponentBinder(resourceConfig.getProperties());
        resourceConfig.register(webComponentBinder);

        final Object locator = webConfig.getServletContext()
                .getAttribute(ServletProperties.SERVICE_LOCATOR);

        this.appHandler = new ApplicationHandler(resourceConfig, webComponentBinder, locator);

        this.asyncExtensionDelegate = getAsyncExtensionDelegate();
        this.forwardOn404 = webConfig.getConfigType() == WebConfig.ConfigType.FilterConfig
                && resourceConfig.isProperty(ServletProperties.FILTER_FORWARD_ON_404);
        this.queryParamsAsFormParams = !resourceConfig.isProperty(ServletProperties.QUERY_PARAMS_AS_FORM_PARAMS_DISABLED);
        this.configSetStatusOverSendError = ServerProperties.getValue(resourceConfig.getProperties(),
                ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, false, Boolean.class);
        this.backgroundTaskScheduler = appHandler.getInjectionManager()
                .getInstance(ScheduledExecutorService.class, BackgroundSchedulerLiteral.INSTANCE);
    }

    /**
     * Dispatch client requests to a resource class.
     *
     * @param baseUri         the base URI of the request.
     * @param requestUri      the URI of the request.
     * @param servletRequest  the {@link jakarta.servlet.http.HttpServletRequest} object that
     *                        contains the request the client made to
     *                        the Web component.
     * @param servletResponse the {@link jakarta.servlet.http.HttpServletResponse} object that
     *                        contains the response the Web component returns
     *                        to the client.
     * @return lazily initialized response status code {@link Value value provider}. If not resolved in the moment of call to
     * {@link Value#get()}, {@code -1} is returned.
     * @throws java.io.IOException            if an input or output error occurs
     *                                        while the Web component is handling the
     *                                        HTTP request.
     * @throws jakarta.servlet.ServletException if the HTTP request cannot be handled.
     */
    public Value<Integer> service(
            final URI baseUri,
            final URI requestUri,
            final HttpServletRequest servletRequest,
            final HttpServletResponse servletResponse) throws ServletException, IOException {
        final ResponseWriter responseWriter = serviceImpl(baseUri, requestUri, servletRequest, servletResponse);
        return Values.lazy(new Value<Integer>() {
            @Override
            public Integer get() {
                return responseWriter.responseContextResolved() ? responseWriter.getResponseStatus() : -1;
            }
        });
    }

    /**
     * Dispatch client requests to a resource class.
     *
     * @param baseUri         the base URI of the request.
     * @param requestUri      the URI of the request.
     * @param servletRequest  the {@link jakarta.servlet.http.HttpServletRequest} object that
     *                        contains the request the client made to
     *                        the Web component.
     * @param servletResponse the {@link jakarta.servlet.http.HttpServletResponse} object that
     *                        contains the response the Web component returns
     *                        to the client.
     * @return returns {@link ResponseWriter}, Servlet's {@link org.glassfish.jersey.server.spi.ContainerResponseWriter}
     *         implementation, into which processed request response was written to.
     * @throws java.io.IOException            if an input or output error occurs
     *                                        while the Web component is handling the
     *                                        HTTP request.
     * @throws jakarta.servlet.ServletException if the HTTP request cannot be handled.
     */
     /* package */ ResponseWriter serviceImpl(
            final URI baseUri,
            final URI requestUri,
            final HttpServletRequest servletRequest,
            final HttpServletResponse servletResponse) throws ServletException, IOException {

        final ResponseWriter responseWriter = new ResponseWriter(
                forwardOn404,
                configSetStatusOverSendError,
                servletResponse,
                asyncExtensionDelegate.createDelegate(servletRequest, servletResponse),
                backgroundTaskScheduler);

        try {
            final ContainerRequest requestContext = new ContainerRequest(baseUri, requestUri, servletRequest.getMethod(),
                    getSecurityContext(servletRequest), new ServletPropertiesDelegate(servletRequest),
                    appHandler.getConfiguration()
            );

            initContainerRequest(requestContext, servletRequest, servletResponse, responseWriter);

            appHandler.handle(requestContext);
        } catch (final HeaderValueException hve) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, LocalizationMessages.HEADER_VALUE_READ_FAILED(), hve);
            }

            final Response.Status status = Response.Status.BAD_REQUEST;

            if (configSetStatusOverSendError) {
                servletResponse.reset();
                //noinspection deprecation
                servletResponse.setStatus(status.getStatusCode(), status.getReasonPhrase());
            } else {
                servletResponse.sendError(status.getStatusCode(), status.getReasonPhrase());
            }
        } catch (final Exception e) {
            throw new ServletException(e);
        }
        return responseWriter;
    }

    /**
     * Initialize {@code ContainerRequest} instance to used used to handle {@code servletRequest}.
     */
    private void initContainerRequest(
            final ContainerRequest requestContext,
            final HttpServletRequest servletRequest,
            final HttpServletResponse servletResponse,
            final ResponseWriter responseWriter) throws IOException {

        requestContext.setEntityStream(servletRequest.getInputStream());
        requestContext.setRequestScopedInitializer(requestScopedInitializer.get(new RequestContextProvider() {
            @Override
            public HttpServletRequest getHttpServletRequest() {
                return servletRequest;
            }
            @Override
            public HttpServletResponse getHttpServletResponse() {
                return servletResponse;
            }
        }));
        requestContext.setWriter(responseWriter);

        addRequestHeaders(servletRequest, requestContext);
        // Check if any servlet filters have consumed a request entity
        // of the media type application/x-www-form-urlencoded
        // This can happen if a filter calls request.getParameter(...)
        filterFormParameters(servletRequest, requestContext);
    }

    /**
     * Get default {@link jakarta.ws.rs.core.SecurityContext} for given {@code request}.
     *
     * @param request http servlet request to create a security context for.
     * @return a non-null security context instance.
     */
    private static SecurityContext getSecurityContext(final HttpServletRequest request) {
        return new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                return request.getUserPrincipal();
            }

            @Override
            public boolean isUserInRole(final String role) {
                return request.isUserInRole(role);
            }

            @Override
            public boolean isSecure() {
                return request.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return request.getAuthType();
            }
        };
    }

    /**
     * Create a {@link ResourceConfig} instance from given {@link WebConfig}.
     *
     * @param config web config to create resource config from.
     * @return resource config instance.
     * @throws ServletException if an error has occurred.
     */
    private static ResourceConfig createResourceConfig(final WebConfig config) throws ServletException {
        final ServletContext servletContext = config.getServletContext();

        // check if ResourceConfig has already been created, if so use it
        ResourceConfig resourceConfig = Utils.retrieve(config.getServletContext(), config.getName());
        if (resourceConfig != null) {
            return resourceConfig;
        }

        final Map<String, Object> initParams = getInitParams(config);
        final Map<String, Object> contextParams = Utils.getContextParams(servletContext);

        // check if the JAX-RS application config class property is present
        final String jaxrsApplicationClassName = config.getInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS);

        if (jaxrsApplicationClassName == null) {
            // If no resource config class property is present, create default config
            resourceConfig = new ResourceConfig().addProperties(initParams).addProperties(contextParams);

            final String webApp = config.getInitParameter(ServletProperties.PROVIDER_WEB_APP);
            if (webApp != null && !"false".equals(webApp)) {
                resourceConfig.registerFinder(new WebAppResourcesScanner(servletContext));
            }
            return resourceConfig;
        }

        try {
            final Class<? extends jakarta.ws.rs.core.Application> jaxrsApplicationClass = AccessController.doPrivileged(
                    ReflectionHelper.<jakarta.ws.rs.core.Application>classForNameWithExceptionPEA(jaxrsApplicationClassName)
            );

            if (jakarta.ws.rs.core.Application.class.isAssignableFrom(jaxrsApplicationClass)) {
                return ResourceConfig.forApplicationClass(jaxrsApplicationClass)
                        .addProperties(initParams).addProperties(contextParams);
            } else {
                throw new ServletException(LocalizationMessages.RESOURCE_CONFIG_PARENT_CLASS_INVALID(
                        jaxrsApplicationClassName, jakarta.ws.rs.core.Application.class));
            }
        } catch (final PrivilegedActionException e) {
            throw new ServletException(
                    LocalizationMessages.RESOURCE_CONFIG_UNABLE_TO_LOAD(jaxrsApplicationClassName), e.getCause());
        } catch (final ClassNotFoundException e) {
            throw new ServletException(LocalizationMessages.RESOURCE_CONFIG_UNABLE_TO_LOAD(jaxrsApplicationClassName), e);
        }
    }

    /**
     * SPI/extension hook to configure ResourceConfig.
     *
     * @param resourceConfig Jersey application configuration.
     * @throws ServletException if an error has occurred.
     */
    private void configure(final ResourceConfig resourceConfig,
                           final ServletContainerProvider[] allServletContainerProviders) throws ServletException {

        for (final ServletContainerProvider servletContainerProvider : allServletContainerProviders) {
            servletContainerProvider.configure(resourceConfig);
        }
    }

    /**
     * Copy request headers present in {@code request} into {@code requestContext} ignoring {@code null} values.
     *
     * @param request        http servlet request to copy headers from.
     * @param requestContext container request to copy headers to.
     */
    @SuppressWarnings("unchecked")
    private void addRequestHeaders(final HttpServletRequest request, final ContainerRequest requestContext) {
        final Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            final String name = names.nextElement();

            final Enumeration<String> values = request.getHeaders(name);
            while (values.hasMoreElements()) {
                final String value = values.nextElement();
                if (value != null) { // filter out null values
                    requestContext.header(name, value);
                }
            }
        }
    }

    /**
     * Extract init params from {@link WebConfig}.
     *
     * @param webConfig actual servlet context.
     * @return map representing current init parameters.
     */
    private static Map<String, Object> getInitParams(final WebConfig webConfig) {
        final Map<String, Object> props = new HashMap<>();
        final Enumeration names = webConfig.getInitParameterNames();
        while (names.hasMoreElements()) {
            final String name = (String) names.nextElement();
            props.put(name, webConfig.getInitParameter(name));
        }
        return props;
    }

    /**
     * Extract parameters contained in {@link HttpServletRequest servlet request} and put them into
     * {@link ContainerRequest container request} under
     * {@value org.glassfish.jersey.server.internal.InternalServerProperties#FORM_DECODED_PROPERTY} property (as {@link Form}
     * instance).
     *
     * @param servletRequest   http servlet request to extract params from.
     * @param containerRequest container request to put {@link Form} property to.
     */
    private void filterFormParameters(final HttpServletRequest servletRequest, final ContainerRequest containerRequest) {
        if (MediaTypes.typeEqual(MediaType.APPLICATION_FORM_URLENCODED_TYPE, containerRequest.getMediaType())
                && !containerRequest.hasEntity()) {
            final Form form = new Form();
            final Enumeration parameterNames = servletRequest.getParameterNames();

            final String queryString = servletRequest.getQueryString();
            final List<String> queryParams = queryString != null ? getDecodedQueryParamList(queryString)
                    : Collections.<String>emptyList();

            final boolean keepQueryParams = queryParamsAsFormParams || queryParams.isEmpty();
            final MultivaluedMap<String, String> formMap = form.asMap();

            while (parameterNames.hasMoreElements()) {
                final String name = (String) parameterNames.nextElement();
                final List<String> values = Arrays.asList(servletRequest.getParameterValues(name));

                formMap.put(name, keepQueryParams ? values : filterQueryParams(name, values, queryParams));
            }

            if (!formMap.isEmpty()) {
                containerRequest.setProperty(InternalServerProperties.FORM_DECODED_PROPERTY, form);

                if (LOGGER.isLoggable(Level.WARNING)) {
                    LOGGER.log(Level.WARNING, LocalizationMessages.FORM_PARAM_CONSUMED(containerRequest.getRequestUri()));
                }
            }
        }
    }

    private List<String> getDecodedQueryParamList(final String queryString) {
        final List<String> params = new ArrayList<>();
        for (final String param : queryString.split("&")) {
            params.add(UriComponent.decode(param, UriComponent.Type.QUERY_PARAM));
        }
        return params;
    }

    /**
     * From given list of values remove values that represents values of query params of the same name as the processed form
     * parameter.
     *
     * @param name   name of form/query parameter.
     * @param values values of form/query parameter.
     * @param params collection of unprocessed query parameters.
     * @return list of form param values for given name without values of query param of the same name.
     */
    private List<String> filterQueryParams(final String name, final List<String> values, final Collection<String> params) {
        return values.stream()
                     .filter(s -> !params.remove(name + "=" + s) && !params.remove(name + "[]=" + s))
                     .collect(Collectors.toList());
    }

    /**
     * Get {@link ApplicationHandler} used by this web component.
     *
     * @return The application handler
     */
    public ApplicationHandler getAppHandler() {
        return appHandler;
    }
}
