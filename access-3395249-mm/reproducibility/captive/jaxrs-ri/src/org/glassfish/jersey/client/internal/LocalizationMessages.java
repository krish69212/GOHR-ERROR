
package org.glassfish.jersey.client.internal;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory.ResourceBundleSupplier;
import org.glassfish.jersey.internal.l10n.Localizer;


/**
 * Defines string formatting method for each constant in the resource file
 * 
 */
public final class LocalizationMessages {

    private final static String BUNDLE_NAME = "org.glassfish.jersey.client.internal.localization";
    private final static LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory(BUNDLE_NAME, new LocalizationMessages.BundleSupplier());
    private final static Localizer LOCALIZER = new Localizer();

    public static Localizable localizableCHUNKED_INPUT_STREAM_CLOSING_ERROR() {
        return MESSAGE_FACTORY.getMessage("chunked.input.stream.closing.error");
    }

    /**
     * Error closing chunked input's underlying response input stream.
     * 
     */
    public static String CHUNKED_INPUT_STREAM_CLOSING_ERROR() {
        return LOCALIZER.localize(localizableCHUNKED_INPUT_STREAM_CLOSING_ERROR());
    }

    public static Localizable localizableCLIENT_TARGET_LINK_NULL() {
        return MESSAGE_FACTORY.getMessage("client.target.link.null");
    }

    /**
     * Link to the newly created target must not be null.
     * 
     */
    public static String CLIENT_TARGET_LINK_NULL() {
        return LOCALIZER.localize(localizableCLIENT_TARGET_LINK_NULL());
    }

    public static Localizable localizableRESTRICTED_HEADER_PROPERTY_SETTING_TRUE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("restricted.header.property.setting.true", arg0);
    }

    /**
     * Restricted headers are enabled using [{0}] system property(setting only takes effect onconnections created after the property has been set/changed).
     * 
     */
    public static String RESTRICTED_HEADER_PROPERTY_SETTING_TRUE(Object arg0) {
        return LOCALIZER.localize(localizableRESTRICTED_HEADER_PROPERTY_SETTING_TRUE(arg0));
    }

    public static Localizable localizableCLIENT_URI_BUILDER_NULL() {
        return MESSAGE_FACTORY.getMessage("client.uri.builder.null");
    }

    /**
     * URI builder of the newly created target must not be null.
     * 
     */
    public static String CLIENT_URI_BUILDER_NULL() {
        return LOCALIZER.localize(localizableCLIENT_URI_BUILDER_NULL());
    }

    public static Localizable localizableNEGATIVE_CHUNK_SIZE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("negative.chunk.size", arg0, arg1);
    }

    /**
     * Negative chunked HTTP transfer coding chunk size value specified in the client configuration property: [{0}] Reverting to programmatically set default: [{1}]
     * 
     */
    public static String NEGATIVE_CHUNK_SIZE(Object arg0, Object arg1) {
        return LOCALIZER.localize(localizableNEGATIVE_CHUNK_SIZE(arg0, arg1));
    }

    public static Localizable localizableERROR_LISTENER_INIT(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.listener.init", arg0);
    }

    /**
     * ClientLifecycleListener {0} failed to initialize properly.
     * 
     */
    public static String ERROR_LISTENER_INIT(Object arg0) {
        return LOCALIZER.localize(localizableERROR_LISTENER_INIT(arg0));
    }

    public static Localizable localizableERROR_CLOSING_OUTPUT_STREAM() {
        return MESSAGE_FACTORY.getMessage("error.closing.output.stream");
    }

    /**
     * Error when closing the output stream.
     * 
     */
    public static String ERROR_CLOSING_OUTPUT_STREAM() {
        return LOCALIZER.localize(localizableERROR_CLOSING_OUTPUT_STREAM());
    }

    public static Localizable localizableERROR_PARAMETER_TYPE_PROCESSING(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.parameter.type.processing", arg0);
    }

    /**
     * Could not process parameter type {0}.
     * 
     */
    public static String ERROR_PARAMETER_TYPE_PROCESSING(Object arg0) {
        return LOCALIZER.localize(localizableERROR_PARAMETER_TYPE_PROCESSING(arg0));
    }

    public static Localizable localizableUSE_ENCODING_IGNORED(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("use.encoding.ignored", arg0, arg1, arg2);
    }

    /**
     * Value {1} of {0} client property will be ignored as it is not a valid supported encoding. Valid supported encodings are: {2}
     * 
     */
    public static String USE_ENCODING_IGNORED(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(localizableUSE_ENCODING_IGNORED(arg0, arg1, arg2));
    }

    public static Localizable localizableCLIENT_RX_PROVIDER_NOT_REGISTERED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("client.rx.provider.not.registered", arg0);
    }

    /**
     * RxInvokerProvider for type {0} is not registered.
     * 
     */
    public static String CLIENT_RX_PROVIDER_NOT_REGISTERED(Object arg0) {
        return LOCALIZER.localize(localizableCLIENT_RX_PROVIDER_NOT_REGISTERED(arg0));
    }

    public static Localizable localizableCLIENT_INSTANCE_CLOSED() {
        return MESSAGE_FACTORY.getMessage("client.instance.closed");
    }

    /**
     * Client instance has been closed.
     * 
     */
    public static String CLIENT_INSTANCE_CLOSED() {
        return LOCALIZER.localize(localizableCLIENT_INSTANCE_CLOSED());
    }

    public static Localizable localizableUSING_FIXED_ASYNC_THREADPOOL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("using.fixed.async.threadpool", arg0);
    }

    /**
     * Using fixed-size thread pool of size [{0}] for asynchronous client invocations.
     * 
     */
    public static String USING_FIXED_ASYNC_THREADPOOL(Object arg0) {
        return LOCALIZER.localize(localizableUSING_FIXED_ASYNC_THREADPOOL(arg0));
    }

    public static Localizable localizableNULL_SSL_CONTEXT() {
        return MESSAGE_FACTORY.getMessage("null.ssl.context");
    }

    /**
     * Custom client SSL context, if set, must not be null.
     * 
     */
    public static String NULL_SSL_CONTEXT() {
        return LOCALIZER.localize(localizableNULL_SSL_CONTEXT());
    }

    public static Localizable localizableREQUEST_ENTITY_WRITER_NULL() {
        return MESSAGE_FACTORY.getMessage("request.entity.writer.null");
    }

    /**
     * The entity of the client request is null.
     * 
     */
    public static String REQUEST_ENTITY_WRITER_NULL() {
        return LOCALIZER.localize(localizableREQUEST_ENTITY_WRITER_NULL());
    }

    public static Localizable localizableAUTHENTICATION_CREDENTIALS_REQUEST_PASSWORD_UNSUPPORTED() {
        return MESSAGE_FACTORY.getMessage("authentication.credentials.request.password.unsupported");
    }

    /**
     * Unsupported password type class. Password passed in the request property must be String or byte[].
     * 
     */
    public static String AUTHENTICATION_CREDENTIALS_REQUEST_PASSWORD_UNSUPPORTED() {
        return LOCALIZER.localize(localizableAUTHENTICATION_CREDENTIALS_REQUEST_PASSWORD_UNSUPPORTED());
    }

    public static Localizable localizableNEGATIVE_INPUT_PARAMETER(Object arg0) {
        return MESSAGE_FACTORY.getMessage("negative.input.parameter", arg0);
    }

    /**
     * "Input parameter {0} must not be negative."
     * 
     */
    public static String NEGATIVE_INPUT_PARAMETER(Object arg0) {
        return LOCALIZER.localize(localizableNEGATIVE_INPUT_PARAMETER(arg0));
    }

    public static Localizable localizableUNEXPECTED_ERROR_RESPONSE_PROCESSING() {
        return MESSAGE_FACTORY.getMessage("unexpected.error.response.processing");
    }

    /**
     * Unexpected error during response processing.
     * 
     */
    public static String UNEXPECTED_ERROR_RESPONSE_PROCESSING() {
        return LOCALIZER.localize(localizableUNEXPECTED_ERROR_RESPONSE_PROCESSING());
    }

    public static Localizable localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_REQUEST(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.service.locator.provider.instance.request", arg0);
    }

    /**
     * Incorrect type of request instance {0}. Parameter must be a default Jersey ClientRequestContext implementation.
     * 
     */
    public static String ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_REQUEST(Object arg0) {
        return LOCALIZER.localize(localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_REQUEST(arg0));
    }

    public static Localizable localizableCLIENT_RESPONSE_RESOLVED_URI_NOT_ABSOLUTE() {
        return MESSAGE_FACTORY.getMessage("client.response.resolved.uri.not.absolute");
    }

    /**
     * Client response resolved URI must be absolute.
     * 
     */
    public static String CLIENT_RESPONSE_RESOLVED_URI_NOT_ABSOLUTE() {
        return LOCALIZER.localize(localizableCLIENT_RESPONSE_RESOLVED_URI_NOT_ABSOLUTE());
    }

    public static Localizable localizableRESPONSE_TO_EXCEPTION_CONVERSION_FAILED() {
        return MESSAGE_FACTORY.getMessage("response.to.exception.conversion.failed");
    }

    /**
     * Failed to convert a response into an exception.
     * 
     */
    public static String RESPONSE_TO_EXCEPTION_CONVERSION_FAILED() {
        return LOCALIZER.localize(localizableRESPONSE_TO_EXCEPTION_CONVERSION_FAILED());
    }

    public static Localizable localizableCLIENT_RESPONSE_RESOLVED_URI_NULL() {
        return MESSAGE_FACTORY.getMessage("client.response.resolved.uri.null");
    }

    /**
     * Client response resolved URI must not be null.
     * 
     */
    public static String CLIENT_RESPONSE_RESOLVED_URI_NULL() {
        return LOCALIZER.localize(localizableCLIENT_RESPONSE_RESOLVED_URI_NULL());
    }

    public static Localizable localizableERROR_DIGEST_FILTER_GENERATOR() {
        return MESSAGE_FACTORY.getMessage("error.digest.filter.generator");
    }

    /**
     * Error during initialization of random generator of Digest authentication.
     * 
     */
    public static String ERROR_DIGEST_FILTER_GENERATOR() {
        return LOCALIZER.localize(localizableERROR_DIGEST_FILTER_GENERATOR());
    }

    public static Localizable localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_RESPONSE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.service.locator.provider.instance.response", arg0);
    }

    /**
     * Incorrect type of response instance {0}. Parameter must be a default Jersey ClientResponseContext implementation.
     * 
     */
    public static String ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_RESPONSE(Object arg0) {
        return LOCALIZER.localize(localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_RESPONSE(arg0));
    }

    public static Localizable localizableRESTRICTED_HEADER_PROPERTY_SETTING_FALSE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("restricted.header.property.setting.false", arg0);
    }

    /**
     * Restricted headers are not enabled using [{0}] system property (setting only takes effect on connections created after the property has been set/changed).
     * 
     */
    public static String RESTRICTED_HEADER_PROPERTY_SETTING_FALSE(Object arg0) {
        return LOCALIZER.localize(localizableRESTRICTED_HEADER_PROPERTY_SETTING_FALSE(arg0));
    }

    public static Localizable localizableERROR_COMMITTING_OUTPUT_STREAM() {
        return MESSAGE_FACTORY.getMessage("error.committing.output.stream");
    }

    /**
     * Error while committing the request output stream.
     * 
     */
    public static String ERROR_COMMITTING_OUTPUT_STREAM() {
        return LOCALIZER.localize(localizableERROR_COMMITTING_OUTPUT_STREAM());
    }

    public static Localizable localizableAUTHENTICATION_CREDENTIALS_MISSING_BASIC() {
        return MESSAGE_FACTORY.getMessage("authentication.credentials.missing.basic");
    }

    /**
     * Credentials must be defined for basic authentication. Define username and password either when creating HttpAuthenticationFeature or use specific credentials for each request using the request property (see HttpAuthenticationFeature).
     * 
     */
    public static String AUTHENTICATION_CREDENTIALS_MISSING_BASIC() {
        return LOCALIZER.localize(localizableAUTHENTICATION_CREDENTIALS_MISSING_BASIC());
    }

    public static Localizable localizablePREINVOCATION_INTERCEPTOR_MULTIPLE_ABORTIONS() {
        return MESSAGE_FACTORY.getMessage("preinvocation.interceptor.multiple.abortions");
    }

    /**
     * ClientRequestContext#abortWith has been utilized multiple times.
     * 
     */
    public static String PREINVOCATION_INTERCEPTOR_MULTIPLE_ABORTIONS() {
        return LOCALIZER.localize(localizablePREINVOCATION_INTERCEPTOR_MULTIPLE_ABORTIONS());
    }

    public static Localizable localizableNULL_EXECUTOR_SERVICE() {
        return MESSAGE_FACTORY.getMessage("null.executor.service");
    }

    /**
     * ExecutorService must not be set to null.
     * 
     */
    public static String NULL_EXECUTOR_SERVICE() {
        return LOCALIZER.localize(localizableNULL_EXECUTOR_SERVICE());
    }

    public static Localizable localizableCLIENT_URI_TEMPLATE_NULL() {
        return MESSAGE_FACTORY.getMessage("client.uri.template.null");
    }

    /**
     * URI template of the newly created target must not be null.
     * 
     */
    public static String CLIENT_URI_TEMPLATE_NULL() {
        return LOCALIZER.localize(localizableCLIENT_URI_TEMPLATE_NULL());
    }

    public static Localizable localizableNULL_INVOCATION_BUILDER() {
        return MESSAGE_FACTORY.getMessage("null.invocation.builder");
    }

    /**
     * Invocation builder must not be null.
     * 
     */
    public static String NULL_INVOCATION_BUILDER() {
        return LOCALIZER.localize(localizableNULL_INVOCATION_BUILDER());
    }

    public static Localizable localizableHTTPURLCONNECTION_REPLACES_GET_WITH_ENTITY() {
        return MESSAGE_FACTORY.getMessage("httpurlconnection.replaces.get.with.entity");
    }

    /**
     * Detected non-empty entity on a HTTP GET request. The underlying HTTP transport connector may decide to change the request method to POST.
     * 
     */
    public static String HTTPURLCONNECTION_REPLACES_GET_WITH_ENTITY() {
        return LOCALIZER.localize(localizableHTTPURLCONNECTION_REPLACES_GET_WITH_ENTITY());
    }

    public static Localizable localizableERROR_LISTENER_CLOSE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.listener.close", arg0);
    }

    /**
     * ClientLifecycleListener {0} failed to close properly.
     * 
     */
    public static String ERROR_LISTENER_CLOSE(Object arg0) {
        return LOCALIZER.localize(localizableERROR_LISTENER_CLOSE(arg0));
    }

    public static Localizable localizableCLIENT_RX_PROVIDER_NULL() {
        return MESSAGE_FACTORY.getMessage("client.rx.provider.null");
    }

    /**
     * RxInvokerProvider returned null.
     * 
     */
    public static String CLIENT_RX_PROVIDER_NULL() {
        return LOCALIZER.localize(localizableCLIENT_RX_PROVIDER_NULL());
    }

    public static Localizable localizableIGNORED_ASYNC_THREADPOOL_SIZE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("ignored.async.threadpool.size", arg0);
    }

    /**
     * Zero or negative asynchronous thread pool size specified in the client configuration property: [{0}] Using default cached thread pool.
     * 
     */
    public static String IGNORED_ASYNC_THREADPOOL_SIZE(Object arg0) {
        return LOCALIZER.localize(localizableIGNORED_ASYNC_THREADPOOL_SIZE(arg0));
    }

    public static Localizable localizableCHUNKED_INPUT_CLOSED() {
        return MESSAGE_FACTORY.getMessage("chunked.input.closed");
    }

    /**
     * Chunked input has been closed already.
     * 
     */
    public static String CHUNKED_INPUT_CLOSED() {
        return LOCALIZER.localize(localizableCHUNKED_INPUT_CLOSED());
    }

    public static Localizable localizableAUTHENTICATION_CREDENTIALS_MISSING_DIGEST() {
        return MESSAGE_FACTORY.getMessage("authentication.credentials.missing.digest");
    }

    /**
     * Credentials must be defined for digest authentication. Define username and password either when creating HttpAuthenticationFeature or use specific credentials for each request using the request property (see HttpAuthenticationFeature).
     * 
     */
    public static String AUTHENTICATION_CREDENTIALS_MISSING_DIGEST() {
        return LOCALIZER.localize(localizableAUTHENTICATION_CREDENTIALS_MISSING_DIGEST());
    }

    public static Localizable localizableNULL_KEYSTORE() {
        return MESSAGE_FACTORY.getMessage("null.keystore");
    }

    /**
     * Custom key store, if set, must not be null.
     * 
     */
    public static String NULL_KEYSTORE() {
        return LOCALIZER.localize(localizableNULL_KEYSTORE());
    }

    public static Localizable localizablePOSTINVOCATION_INTERCEPTOR_MULTIPLE_RESOLVES() {
        return MESSAGE_FACTORY.getMessage("postinvocation.interceptor.multiple.resolves");
    }

    /**
     * ExceptionContext#resolve has been utilized multiple times.
     * 
     */
    public static String POSTINVOCATION_INTERCEPTOR_MULTIPLE_RESOLVES() {
        return LOCALIZER.localize(localizablePOSTINVOCATION_INTERCEPTOR_MULTIPLE_RESOLVES());
    }

    public static Localizable localizableCHUNKED_INPUT_MEDIA_TYPE_NULL() {
        return MESSAGE_FACTORY.getMessage("chunked.input.media.type.null");
    }

    /**
     * Specified chunk media type must not be null.
     * 
     */
    public static String CHUNKED_INPUT_MEDIA_TYPE_NULL() {
        return LOCALIZER.localize(localizableCHUNKED_INPUT_MEDIA_TYPE_NULL());
    }

    public static Localizable localizableNULL_TRUSTSTORE() {
        return MESSAGE_FACTORY.getMessage("null.truststore");
    }

    /**
     * Custom trust store, if set, must not be null.
     * 
     */
    public static String NULL_TRUSTSTORE() {
        return LOCALIZER.localize(localizableNULL_TRUSTSTORE());
    }

    public static Localizable localizableREQUEST_ENTITY_ALREADY_WRITTEN() {
        return MESSAGE_FACTORY.getMessage("request.entity.already.written");
    }

    /**
     * The entity was already written in this request. The entity can be written (serialized into the output stream) only once per a request.
     * 
     */
    public static String REQUEST_ENTITY_ALREADY_WRITTEN() {
        return LOCALIZER.localize(localizableREQUEST_ENTITY_ALREADY_WRITTEN());
    }

    public static Localizable localizableERROR_SHUTDOWNHOOK_CLOSE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.shutdownhook.close", arg0);
    }

    /**
     * Client shutdown hook {0} failed.
     * 
     */
    public static String ERROR_SHUTDOWNHOOK_CLOSE(Object arg0) {
        return LOCALIZER.localize(localizableERROR_SHUTDOWNHOOK_CLOSE(arg0));
    }

    public static Localizable localizableNULL_KEYSTORE_PASWORD() {
        return MESSAGE_FACTORY.getMessage("null.keystore.pasword");
    }

    /**
     * Custom key store password must not be null.
     * 
     */
    public static String NULL_KEYSTORE_PASWORD() {
        return LOCALIZER.localize(localizableNULL_KEYSTORE_PASWORD());
    }

    public static Localizable localizablePOSTINVOCATION_INTERCEPTOR_RESOLVE() {
        return MESSAGE_FACTORY.getMessage("postinvocation.interceptor.resolve");
    }

    /**
     * ExceptionContext#resolve has been utilized.
     * 
     */
    public static String POSTINVOCATION_INTERCEPTOR_RESOLVE() {
        return LOCALIZER.localize(localizablePOSTINVOCATION_INTERCEPTOR_RESOLVE());
    }

    public static Localizable localizableNULL_CONNECTOR_PROVIDER() {
        return MESSAGE_FACTORY.getMessage("null.connector.provider");
    }

    /**
     * ConnectorProvider must not be set to null.
     * 
     */
    public static String NULL_CONNECTOR_PROVIDER() {
        return LOCALIZER.localize(localizableNULL_CONNECTOR_PROVIDER());
    }

    public static Localizable localizableERROR_HTTP_METHOD_ENTITY_NULL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.http.method.entity.null", arg0);
    }

    /**
     * Entity must not be null for http method {0}.
     * 
     */
    public static String ERROR_HTTP_METHOD_ENTITY_NULL(Object arg0) {
        return LOCALIZER.localize(localizableERROR_HTTP_METHOD_ENTITY_NULL(arg0));
    }

    public static Localizable localizableDIGEST_FILTER_QOP_UNSUPPORTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("digest.filter.qop.unsupported", arg0);
    }

    /**
     * The 'qop' (quality of protection) = {0} extension requested by the server is not supported by Jersey HttpDigestAuthFilter. Cannot authenticate against the server using Http Digest Authentication.
     * 
     */
    public static String DIGEST_FILTER_QOP_UNSUPPORTED(Object arg0) {
        return LOCALIZER.localize(localizableDIGEST_FILTER_QOP_UNSUPPORTED(arg0));
    }

    public static Localizable localizableCLIENT_URI_NULL() {
        return MESSAGE_FACTORY.getMessage("client.uri.null");
    }

    /**
     * URI of the newly created target must not be null.
     * 
     */
    public static String CLIENT_URI_NULL() {
        return LOCALIZER.localize(localizableCLIENT_URI_NULL());
    }

    public static Localizable localizablePREINVOCATION_INTERCEPTOR_EXCEPTION() {
        return MESSAGE_FACTORY.getMessage("preinvocation.interceptor.exception");
    }

    /**
     * PreInvocationInterceptor threw exception.
     * 
     */
    public static String PREINVOCATION_INTERCEPTOR_EXCEPTION() {
        return LOCALIZER.localize(localizablePREINVOCATION_INTERCEPTOR_EXCEPTION());
    }

    public static Localizable localizableNULL_INPUT_PARAMETER(Object arg0) {
        return MESSAGE_FACTORY.getMessage("null.input.parameter", arg0);
    }

    /**
     * Input method parameter {0} must not be null.
     * 
     */
    public static String NULL_INPUT_PARAMETER(Object arg0) {
        return LOCALIZER.localize(localizableNULL_INPUT_PARAMETER(arg0));
    }

    public static Localizable localizableERROR_REQUEST_CANCELLED() {
        return MESSAGE_FACTORY.getMessage("error.request.cancelled");
    }

    /**
     * Request cancelled by the client call.
     * 
     */
    public static String ERROR_REQUEST_CANCELLED() {
        return LOCALIZER.localize(localizableERROR_REQUEST_CANCELLED());
    }

    public static Localizable localizableCOLLECTION_UPDATER_TYPE_UNSUPPORTED() {
        return MESSAGE_FACTORY.getMessage("collection.updater.type.unsupported");
    }

    /**
     * Unsupported collection type.
     * 
     */
    public static String COLLECTION_UPDATER_TYPE_UNSUPPORTED() {
        return LOCALIZER.localize(localizableCOLLECTION_UPDATER_TYPE_UNSUPPORTED());
    }

    public static Localizable localizableERROR_HTTP_METHOD_ENTITY_NOT_NULL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.http.method.entity.not.null", arg0);
    }

    /**
     * Entity must be null for http method {0}.
     * 
     */
    public static String ERROR_HTTP_METHOD_ENTITY_NOT_NULL(Object arg0) {
        return LOCALIZER.localize(localizableERROR_HTTP_METHOD_ENTITY_NOT_NULL(arg0));
    }

    public static Localizable localizableCLIENT_RESPONSE_STATUS_NULL() {
        return MESSAGE_FACTORY.getMessage("client.response.status.null");
    }

    /**
     * Client response status must not be null.
     * 
     */
    public static String CLIENT_RESPONSE_STATUS_NULL() {
        return LOCALIZER.localize(localizableCLIENT_RESPONSE_STATUS_NULL());
    }

    public static Localizable localizableCLIENT_INVOCATION_LINK_NULL() {
        return MESSAGE_FACTORY.getMessage("client.invocation.link.null");
    }

    /**
     * Link of the newly created invocation must not be null.
     * 
     */
    public static String CLIENT_INVOCATION_LINK_NULL() {
        return LOCALIZER.localize(localizableCLIENT_INVOCATION_LINK_NULL());
    }

    public static Localizable localizablePREINVOCATION_INTERCEPTOR_ABORT_WITH() {
        return MESSAGE_FACTORY.getMessage("preinvocation.interceptor.abortWith");
    }

    /**
     * PreInvocationInterceptor utilized ClientRequestContext#abortWith.
     * 
     */
    public static String PREINVOCATION_INTERCEPTOR_ABORT_WITH() {
        return LOCALIZER.localize(localizablePREINVOCATION_INTERCEPTOR_ABORT_WITH());
    }

    public static Localizable localizablePOSTINVOCATION_INTERCEPTOR_EXCEPTION() {
        return MESSAGE_FACTORY.getMessage("postinvocation.interceptor.exception");
    }

    /**
     * PostInvocationInterceptor threw exception.
     * 
     */
    public static String POSTINVOCATION_INTERCEPTOR_EXCEPTION() {
        return LOCALIZER.localize(localizablePOSTINVOCATION_INTERCEPTOR_EXCEPTION());
    }

    public static Localizable localizableEXCEPTION_SUPPRESSED() {
        return MESSAGE_FACTORY.getMessage("exception.suppressed");
    }

    /**
     * Exceptions were thrown. See suppressed exceptions for the list.
     * 
     */
    public static String EXCEPTION_SUPPRESSED() {
        return LOCALIZER.localize(localizableEXCEPTION_SUPPRESSED());
    }

    public static Localizable localizableAUTHENTICATION_CREDENTIALS_NOT_PROVIDED_BASIC() {
        return MESSAGE_FACTORY.getMessage("authentication.credentials.not.provided.basic");
    }

    /**
     * No credentials are provided for basic authentication. Request will be sent without an Authorization header.
     * 
     */
    public static String AUTHENTICATION_CREDENTIALS_NOT_PROVIDED_BASIC() {
        return LOCALIZER.localize(localizableAUTHENTICATION_CREDENTIALS_NOT_PROVIDED_BASIC());
    }

    public static Localizable localizableRESTRICTED_HEADER_POSSIBLY_IGNORED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("restricted.header.possibly.ignored", arg0);
    }

    /**
     * Attempt to send restricted header(s) while the [{0}] system property not set. Header(s) will possibly be ignored.
     * 
     */
    public static String RESTRICTED_HEADER_POSSIBLY_IGNORED(Object arg0) {
        return LOCALIZER.localize(localizableRESTRICTED_HEADER_POSSIBLY_IGNORED(arg0));
    }

    public static Localizable localizableNULL_SCHEDULED_EXECUTOR_SERVICE() {
        return MESSAGE_FACTORY.getMessage("null.scheduled.executor.service");
    }

    /**
     * ScheduledExecutorService must not be set to null.
     * 
     */
    public static String NULL_SCHEDULED_EXECUTOR_SERVICE() {
        return LOCALIZER.localize(localizableNULL_SCHEDULED_EXECUTOR_SERVICE());
    }

    public static Localizable localizableRESPONSE_TYPE_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("response.type.is.null");
    }

    /**
     * Requested response type is null.
     * 
     */
    public static String RESPONSE_TYPE_IS_NULL() {
        return LOCALIZER.localize(localizableRESPONSE_TYPE_IS_NULL());
    }

    private static class BundleSupplier
        implements ResourceBundleSupplier
    {


        public ResourceBundle getResourceBundle(Locale locale) {
            return ResourceBundle.getBundle(BUNDLE_NAME, locale);
        }

    }

}
