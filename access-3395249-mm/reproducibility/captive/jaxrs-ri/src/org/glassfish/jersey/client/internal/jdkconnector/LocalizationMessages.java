
package org.glassfish.jersey.client.internal.jdkconnector;

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

    private final static String BUNDLE_NAME = "org.glassfish.jersey.client.internal.jdkconnector.localization";
    private final static LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory(BUNDLE_NAME, new LocalizationMessages.BundleSupplier());
    private final static Localizer LOCALIZER = new Localizer();

    public static Localizable localizableNEGATIVE_CHUNK_SIZE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("negative.chunk.size", arg0, arg1);
    }

    /**
     * "Configured chunk size is negative: {0}, using default value: {1}."
     * 
     */
    public static String NEGATIVE_CHUNK_SIZE(Object arg0, Object arg1) {
        return LOCALIZER.localize(localizableNEGATIVE_CHUNK_SIZE(arg0, arg1));
    }

    public static Localizable localizableHTTP_UNEXPECTED_CHUNK_HEADER() {
        return MESSAGE_FACTORY.getMessage("http.unexpected.chunk.header");
    }

    /**
     * "Unexpected HTTP chunk header."
     * 
     */
    public static String HTTP_UNEXPECTED_CHUNK_HEADER() {
        return LOCALIZER.localize(localizableHTTP_UNEXPECTED_CHUNK_HEADER());
    }

    public static Localizable localizableREDIRECT_ERROR_DETERMINING_LOCATION() {
        return MESSAGE_FACTORY.getMessage("redirect.error.determining.location");
    }

    /**
     * "Error determining redirect location."
     * 
     */
    public static String REDIRECT_ERROR_DETERMINING_LOCATION() {
        return LOCALIZER.localize(localizableREDIRECT_ERROR_DETERMINING_LOCATION());
    }

    public static Localizable localizableHTTP_TRAILER_HEADER_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.trailer.header.overflow");
    }

    /**
     * "The chunked encoding trailer header is too large."
     * 
     */
    public static String HTTP_TRAILER_HEADER_OVERFLOW() {
        return LOCALIZER.localize(localizableHTTP_TRAILER_HEADER_OVERFLOW());
    }

    public static Localizable localizableREDIRECT_INFINITE_LOOP() {
        return MESSAGE_FACTORY.getMessage("redirect.infinite.loop");
    }

    /**
     * "Infinite loop in chained redirects detected."
     * 
     */
    public static String REDIRECT_INFINITE_LOOP() {
        return LOCALIZER.localize(localizableREDIRECT_INFINITE_LOOP());
    }

    public static Localizable localizableWRITING_FAILED() {
        return MESSAGE_FACTORY.getMessage("writing.failed");
    }

    /**
     * "Writing data failed"
     * 
     */
    public static String WRITING_FAILED() {
        return LOCALIZER.localize(localizableWRITING_FAILED());
    }

    public static Localizable localizableTIMEOUT_RECEIVING_RESPONSE_BODY() {
        return MESSAGE_FACTORY.getMessage("timeout.receiving.response.body");
    }

    /**
     * "Timeout receiving response body."
     * 
     */
    public static String TIMEOUT_RECEIVING_RESPONSE_BODY() {
        return LOCALIZER.localize(localizableTIMEOUT_RECEIVING_RESPONSE_BODY());
    }

    public static Localizable localizableSYNC_OPERATION_NOT_SUPPORTED() {
        return MESSAGE_FACTORY.getMessage("sync.operation.not.supported");
    }

    /**
     * "Operation not supported in asynchronous mode."
     * 
     */
    public static String SYNC_OPERATION_NOT_SUPPORTED() {
        return LOCALIZER.localize(localizableSYNC_OPERATION_NOT_SUPPORTED());
    }

    public static Localizable localizablePROXY_FAIL_AUTH_HEADER() {
        return MESSAGE_FACTORY.getMessage("proxy.fail.auth.header");
    }

    /**
     * "Creating authorization header failed."
     * 
     */
    public static String PROXY_FAIL_AUTH_HEADER() {
        return LOCALIZER.localize(localizablePROXY_FAIL_AUTH_HEADER());
    }

    public static Localizable localizableWRITE_WHEN_NOT_READY() {
        return MESSAGE_FACTORY.getMessage("write.when.not.ready");
    }

    /**
     * "Asynchronous write called when stream is in non-ready state."
     * 
     */
    public static String WRITE_WHEN_NOT_READY() {
        return LOCALIZER.localize(localizableWRITE_WHEN_NOT_READY());
    }

    public static Localizable localizableCONNECTION_CHANGING_STATE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("connection.changing.state", arg0, arg1, arg2, arg3);
    }

    /**
     * "HTTP connection {0}:{1} changing state {2} -> {3}."
     * 
     */
    public static String CONNECTION_CHANGING_STATE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(localizableCONNECTION_CHANGING_STATE(arg0, arg1, arg2, arg3));
    }

    public static Localizable localizableCLOSED_WHILE_RECEIVING_RESPONSE() {
        return MESSAGE_FACTORY.getMessage("closed.while.receiving.response");
    }

    /**
     * "Connection closed by the server while receiving response."
     * 
     */
    public static String CLOSED_WHILE_RECEIVING_RESPONSE() {
        return LOCALIZER.localize(localizableCLOSED_WHILE_RECEIVING_RESPONSE());
    }

    public static Localizable localizableCONNECTOR_CONFIGURATION(Object arg0) {
        return MESSAGE_FACTORY.getMessage("connector.configuration", arg0);
    }

    /**
     * "Connector configuration: {0}."
     * 
     */
    public static String CONNECTOR_CONFIGURATION(Object arg0) {
        return LOCALIZER.localize(localizableCONNECTOR_CONFIGURATION(arg0));
    }

    public static Localizable localizableSTREAM_CLOSED_FOR_INPUT() {
        return MESSAGE_FACTORY.getMessage("stream.closed.for.input");
    }

    /**
     * "This stream has already been closed for input."
     * 
     */
    public static String STREAM_CLOSED_FOR_INPUT() {
        return LOCALIZER.localize(localizableSTREAM_CLOSED_FOR_INPUT());
    }

    public static Localizable localizablePROXY_QOP_NO_SUPPORTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("proxy.qop.no.supported", arg0);
    }

    /**
     * "The 'qop' (quality of protection) = {0} extension requested by the server is not supported. Cannot authenticate against the server using Http Digest Authentication."
     * 
     */
    public static String PROXY_QOP_NO_SUPPORTED(Object arg0) {
        return LOCALIZER.localize(localizablePROXY_QOP_NO_SUPPORTED(arg0));
    }

    public static Localizable localizableCLOSED_BY_CLIENT_WHILE_RECEIVING_BODY() {
        return MESSAGE_FACTORY.getMessage("closed.by.client.while.receiving.body");
    }

    /**
     * "Connection closed by the client while receiving response body."
     * 
     */
    public static String CLOSED_BY_CLIENT_WHILE_RECEIVING_BODY() {
        return LOCALIZER.localize(localizableCLOSED_BY_CLIENT_WHILE_RECEIVING_BODY());
    }

    public static Localizable localizableREDIRECT_LIMIT_REACHED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("redirect.limit.reached", arg0);
    }

    /**
     * "Max chained redirect limit ({0}) exceeded."
     * 
     */
    public static String REDIRECT_LIMIT_REACHED(Object arg0) {
        return LOCALIZER.localize(localizableREDIRECT_LIMIT_REACHED(arg0));
    }

    public static Localizable localizableHTTP_CHUNK_ENCODING_PREFIX_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.chunk.encoding.prefix.overflow");
    }

    /**
     * "The chunked encoding length prefix is too large."
     * 
     */
    public static String HTTP_CHUNK_ENCODING_PREFIX_OVERFLOW() {
        return LOCALIZER.localize(localizableHTTP_CHUNK_ENCODING_PREFIX_OVERFLOW());
    }

    public static Localizable localizableHTTP_CONNECTION_ESTABLISHING_ILLEGAL_STATE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("http.connection.establishing.illegal.state", arg0);
    }

    /**
     * "Cannot try to establish connection if the connection is in other than CREATED state. Current state: {0}.
     * 
     */
    public static String HTTP_CONNECTION_ESTABLISHING_ILLEGAL_STATE(Object arg0) {
        return LOCALIZER.localize(localizableHTTP_CONNECTION_ESTABLISHING_ILLEGAL_STATE(arg0));
    }

    public static Localizable localizableTRANSPORT_SET_CLASS_LOADER_FAILED() {
        return MESSAGE_FACTORY.getMessage("transport.set.class.loader.failed");
    }

    /**
     * "Cannot set thread context class loader."
     * 
     */
    public static String TRANSPORT_SET_CLASS_LOADER_FAILED() {
        return LOCALIZER.localize(localizableTRANSPORT_SET_CLASS_LOADER_FAILED());
    }

    public static Localizable localizableCONNECTION_TIMEOUT() {
        return MESSAGE_FACTORY.getMessage("connection.timeout");
    }

    /**
     * "Connection timed out."
     * 
     */
    public static String CONNECTION_TIMEOUT() {
        return LOCALIZER.localize(localizableCONNECTION_TIMEOUT());
    }

    public static Localizable localizableHTTP_REQUEST_NO_BUFFERED_BODY() {
        return MESSAGE_FACTORY.getMessage("http.request.no.buffered.body");
    }

    /**
     * "Buffered body is available only in buffered body mode."
     * 
     */
    public static String HTTP_REQUEST_NO_BUFFERED_BODY() {
        return LOCALIZER.localize(localizableHTTP_REQUEST_NO_BUFFERED_BODY());
    }

    public static Localizable localizableHTTP_BODY_SIZE_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.body.size.overflow");
    }

    /**
     * "Body size exceeds declared size"
     * 
     */
    public static String HTTP_BODY_SIZE_OVERFLOW() {
        return LOCALIZER.localize(localizableHTTP_BODY_SIZE_OVERFLOW());
    }

    public static Localizable localizableCLOSED_BY_CLIENT_WHILE_RECEIVING() {
        return MESSAGE_FACTORY.getMessage("closed.by.client.while.receiving");
    }

    /**
     * "Connection closed by the client while receiving response."
     * 
     */
    public static String CLOSED_BY_CLIENT_WHILE_RECEIVING() {
        return LOCALIZER.localize(localizableCLOSED_BY_CLIENT_WHILE_RECEIVING());
    }

    public static Localizable localizableCLOSED_WHILE_RECEIVING_BODY() {
        return MESSAGE_FACTORY.getMessage("closed.while.receiving.body");
    }

    /**
     * "Connection closed by the server while receiving response body."
     * 
     */
    public static String CLOSED_WHILE_RECEIVING_BODY() {
        return LOCALIZER.localize(localizableCLOSED_WHILE_RECEIVING_BODY());
    }

    public static Localizable localizableHTTP_INVALID_CHUNK_SIZE_HEX_VALUE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("http.invalid.chunk.size.hex.value", arg0);
    }

    /**
     * "Invalid byte representing a hex value within a chunk length encountered : {0}"
     * 
     */
    public static String HTTP_INVALID_CHUNK_SIZE_HEX_VALUE(Object arg0) {
        return LOCALIZER.localize(localizableHTTP_INVALID_CHUNK_SIZE_HEX_VALUE(arg0));
    }

    public static Localizable localizableWRITE_LISTENER_SET_ONLY_ONCE() {
        return MESSAGE_FACTORY.getMessage("write.listener.set.only.once");
    }

    /**
     * "Write listener can be set only once."
     * 
     */
    public static String WRITE_LISTENER_SET_ONLY_ONCE() {
        return LOCALIZER.localize(localizableWRITE_LISTENER_SET_ONLY_ONCE());
    }

    public static Localizable localizablePROXY_UNSUPPORTED_SCHEME(Object arg0) {
        return MESSAGE_FACTORY.getMessage("proxy.unsupported.scheme", arg0);
    }

    /**
     * "Unsupported scheme: {0}."
     * 
     */
    public static String PROXY_UNSUPPORTED_SCHEME(Object arg0) {
        return LOCALIZER.localize(localizablePROXY_UNSUPPORTED_SCHEME(arg0));
    }

    public static Localizable localizableHTTP_NEGATIVE_CONTENT_LENGTH() {
        return MESSAGE_FACTORY.getMessage("http.negative.content.length");
    }

    /**
     * "Content length cannot be less than 0."
     * 
     */
    public static String HTTP_NEGATIVE_CONTENT_LENGTH() {
        return LOCALIZER.localize(localizableHTTP_NEGATIVE_CONTENT_LENGTH());
    }

    public static Localizable localizableSSL_SESSION_CLOSED() {
        return MESSAGE_FACTORY.getMessage("ssl.session.closed");
    }

    /**
     * "SSL session has been closed."
     * 
     */
    public static String SSL_SESSION_CLOSED() {
        return LOCALIZER.localize(localizableSSL_SESSION_CLOSED());
    }

    public static Localizable localizableCLOSED_WHILE_SENDING_REQUEST() {
        return MESSAGE_FACTORY.getMessage("closed.while.sending.request");
    }

    /**
     * "Connection closed by the server while sending request".
     * 
     */
    public static String CLOSED_WHILE_SENDING_REQUEST() {
        return LOCALIZER.localize(localizableCLOSED_WHILE_SENDING_REQUEST());
    }

    public static Localizable localizableHTTP_INITIAL_LINE_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.initial.line.overflow");
    }

    /**
     * "HTTP packet initial line is too large."
     * 
     */
    public static String HTTP_INITIAL_LINE_OVERFLOW() {
        return LOCALIZER.localize(localizableHTTP_INITIAL_LINE_OVERFLOW());
    }

    public static Localizable localizableTRANSPORT_EXECUTOR_QUEUE_LIMIT_REACHED() {
        return MESSAGE_FACTORY.getMessage("transport.executor.queue.limit.reached");
    }

    /**
     * "A limit of client thread pool queue has been reached."
     * 
     */
    public static String TRANSPORT_EXECUTOR_QUEUE_LIMIT_REACHED() {
        return LOCALIZER.localize(localizableTRANSPORT_EXECUTOR_QUEUE_LIMIT_REACHED());
    }

    public static Localizable localizablePROXY_MISSING_AUTH_HEADER() {
        return MESSAGE_FACTORY.getMessage("proxy.missing.auth.header");
    }

    /**
     * "Proxy-Authenticate header value is missing or empty."
     * 
     */
    public static String PROXY_MISSING_AUTH_HEADER() {
        return LOCALIZER.localize(localizablePROXY_MISSING_AUTH_HEADER());
    }

    public static Localizable localizableTHREAD_POOL_MAX_SIZE_TOO_SMALL() {
        return MESSAGE_FACTORY.getMessage("thread.pool.max.size.too.small");
    }

    /**
     * "Max thread pool size cannot be smaller than 3."
     * 
     */
    public static String THREAD_POOL_MAX_SIZE_TOO_SMALL() {
        return LOCALIZER.localize(localizableTHREAD_POOL_MAX_SIZE_TOO_SMALL());
    }

    public static Localizable localizableCLOSED_BY_CLIENT_WHILE_SENDING() {
        return MESSAGE_FACTORY.getMessage("closed.by.client.while.sending");
    }

    /**
     * "Connection closed by the client while sending request."
     * 
     */
    public static String CLOSED_BY_CLIENT_WHILE_SENDING() {
        return LOCALIZER.localize(localizableCLOSED_BY_CLIENT_WHILE_SENDING());
    }

    public static Localizable localizableHTTP_REQUEST_BODY_SIZE_NOT_AVAILABLE() {
        return MESSAGE_FACTORY.getMessage("http.request.body.size.not.available");
    }

    /**
     * "Body size is not available in chunked body mode."
     * 
     */
    public static String HTTP_REQUEST_BODY_SIZE_NOT_AVAILABLE() {
        return LOCALIZER.localize(localizableHTTP_REQUEST_BODY_SIZE_NOT_AVAILABLE());
    }

    public static Localizable localizableTRANSPORT_EXECUTOR_CLOSED() {
        return MESSAGE_FACTORY.getMessage("transport.executor.closed");
    }

    /**
     * "Cannot set thread context class loader."
     * 
     */
    public static String TRANSPORT_EXECUTOR_CLOSED() {
        return LOCALIZER.localize(localizableTRANSPORT_EXECUTOR_CLOSED());
    }

    public static Localizable localizableHTTP_PACKET_HEADER_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.packet.header.overflow");
    }

    /**
     * "HTTP packet header is too large."
     * 
     */
    public static String HTTP_PACKET_HEADER_OVERFLOW() {
        return LOCALIZER.localize(localizableHTTP_PACKET_HEADER_OVERFLOW());
    }

    public static Localizable localizableHTTP_CONNECTION_NOT_IDLE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("http.connection.not.idle", arg0);
    }

    /**
     * "Http request cannot be sent over a connection that is in other state than IDLE. Current state: {0}" 
     * 
     */
    public static String HTTP_CONNECTION_NOT_IDLE(Object arg0) {
        return LOCALIZER.localize(localizableHTTP_CONNECTION_NOT_IDLE(arg0));
    }

    public static Localizable localizableTIMEOUT_RECEIVING_RESPONSE() {
        return MESSAGE_FACTORY.getMessage("timeout.receiving.response");
    }

    /**
     * "Timeout receiving response."
     * 
     */
    public static String TIMEOUT_RECEIVING_RESPONSE() {
        return LOCALIZER.localize(localizableTIMEOUT_RECEIVING_RESPONSE());
    }

    public static Localizable localizablePROXY_407_TWICE() {
        return MESSAGE_FACTORY.getMessage("proxy.407.twice");
    }

    /**
     * "Received 407 for the second time."
     * 
     */
    public static String PROXY_407_TWICE() {
        return LOCALIZER.localize(localizablePROXY_407_TWICE());
    }

    public static Localizable localizableTRANSPORT_CONNECTION_NOT_CLOSED() {
        return MESSAGE_FACTORY.getMessage("transport.connection.not.closed");
    }

    /**
     * "Could not close a connection."
     * 
     */
    public static String TRANSPORT_CONNECTION_NOT_CLOSED() {
        return LOCALIZER.localize(localizableTRANSPORT_CONNECTION_NOT_CLOSED());
    }

    public static Localizable localizableHTTP_REQUEST_NO_BODY() {
        return MESSAGE_FACTORY.getMessage("http.request.no.body");
    }

    /**
     * "This HTTP request does not have a body."
     * 
     */
    public static String HTTP_REQUEST_NO_BODY() {
        return LOCALIZER.localize(localizableHTTP_REQUEST_NO_BODY());
    }

    public static Localizable localizablePROXY_PASSWORD_MISSING() {
        return MESSAGE_FACTORY.getMessage("proxy.password.missing");
    }

    /**
     * "Password is missing"
     * 
     */
    public static String PROXY_PASSWORD_MISSING() {
        return LOCALIZER.localize(localizablePROXY_PASSWORD_MISSING());
    }

    public static Localizable localizableHTTP_INVALID_CONTENT_LENGTH() {
        return MESSAGE_FACTORY.getMessage("http.invalid.content.length");
    }

    /**
     * "Invalid format of content length code."
     * 
     */
    public static String HTTP_INVALID_CONTENT_LENGTH() {
        return LOCALIZER.localize(localizableHTTP_INVALID_CONTENT_LENGTH());
    }

    public static Localizable localizableASYNC_OPERATION_NOT_SUPPORTED() {
        return MESSAGE_FACTORY.getMessage("async.operation.not.supported");
    }

    /**
     * "Operation not supported in synchronous mode."
     * 
     */
    public static String ASYNC_OPERATION_NOT_SUPPORTED() {
        return LOCALIZER.localize(localizableASYNC_OPERATION_NOT_SUPPORTED());
    }

    public static Localizable localizablePROXY_USER_NAME_MISSING() {
        return MESSAGE_FACTORY.getMessage("proxy.user.name.missing");
    }

    /**
     * "User name is missing"
     * 
     */
    public static String PROXY_USER_NAME_MISSING() {
        return LOCALIZER.localize(localizablePROXY_USER_NAME_MISSING());
    }

    public static Localizable localizablePROXY_CONNECT_FAIL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("proxy.connect.fail", arg0);
    }

    /**
     * "Connecting to proxy failed with status {0}."
     * 
     */
    public static String PROXY_CONNECT_FAIL(Object arg0) {
        return LOCALIZER.localize(localizablePROXY_CONNECT_FAIL(arg0));
    }

    public static Localizable localizableREDIRECT_NO_LOCATION() {
        return MESSAGE_FACTORY.getMessage("redirect.no.location");
    }

    /**
     * "Received redirect that does not contain a location or the location is empty."
     * 
     */
    public static String REDIRECT_NO_LOCATION() {
        return LOCALIZER.localize(localizableREDIRECT_NO_LOCATION());
    }

    public static Localizable localizableSTREAM_CLOSED() {
        return MESSAGE_FACTORY.getMessage("stream.closed");
    }

    /**
     * "The stream has been closed."
     * 
     */
    public static String STREAM_CLOSED() {
        return LOCALIZER.localize(localizableSTREAM_CLOSED());
    }

    public static Localizable localizableTHREAD_POOL_CORE_SIZE_TOO_SMALL() {
        return MESSAGE_FACTORY.getMessage("thread.pool.core.size.too.small");
    }

    /**
     * "Core thread pool size cannot be smaller than 0."
     * 
     */
    public static String THREAD_POOL_CORE_SIZE_TOO_SMALL() {
        return LOCALIZER.localize(localizableTHREAD_POOL_CORE_SIZE_TOO_SMALL());
    }

    public static Localizable localizableCONNECTION_CLOSED() {
        return MESSAGE_FACTORY.getMessage("connection.closed");
    }

    /**
     * "Connection closed by the server."
     * 
     */
    public static String CONNECTION_CLOSED() {
        return LOCALIZER.localize(localizableCONNECTION_CLOSED());
    }

    public static Localizable localizableBUFFER_INCORRECT_LENGTH() {
        return MESSAGE_FACTORY.getMessage("buffer.incorrect.length");
    }

    /**
     * "Buffer passed for encoding is neither a multiple of chunkSize nor smaller than chunkSize."
     * 
     */
    public static String BUFFER_INCORRECT_LENGTH() {
        return LOCALIZER.localize(localizableBUFFER_INCORRECT_LENGTH());
    }

    public static Localizable localizableREAD_LISTENER_SET_ONLY_ONCE() {
        return MESSAGE_FACTORY.getMessage("read.listener.set.only.once");
    }

    /**
     * "Read listener can be set only once."
     * 
     */
    public static String READ_LISTENER_SET_ONLY_ONCE() {
        return LOCALIZER.localize(localizableREAD_LISTENER_SET_ONLY_ONCE());
    }

    public static Localizable localizableUNEXPECTED_DATA_IN_BUFFER() {
        return MESSAGE_FACTORY.getMessage("unexpected.data.in.buffer");
    }

    /**
     * "Unexpected data remain in the buffer after the HTTP response has been parsed."
     * 
     */
    public static String UNEXPECTED_DATA_IN_BUFFER() {
        return LOCALIZER.localize(localizableUNEXPECTED_DATA_IN_BUFFER());
    }

    private static class BundleSupplier
        implements ResourceBundleSupplier
    {


        public ResourceBundle getResourceBundle(Locale locale) {
            return ResourceBundle.getBundle(BUNDLE_NAME, locale);
        }

    }

}
