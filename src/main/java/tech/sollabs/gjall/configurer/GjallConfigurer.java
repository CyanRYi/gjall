package tech.sollabs.gjall.configurer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tech.sollabs.gjall.handlers.AfterRequestLoggingHandler;
import tech.sollabs.gjall.handlers.BeforeRequestLoggingHandler;

/**
 * Has config about include or exclude logging properties
 * setter can access from {@code {@link GjallConfigurerBuilder}}
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see AfterRequestLoggingHandler
 * @see BeforeRequestLoggingHandler
 * @see tech.sollabs.gjall.configurer.GjallConfigurerBuilder
 * @see tech.sollabs.gjall.GjallRequestLoggingFilter
 */
public class GjallConfigurer {

    private final Log logger = LogFactory.getLog(getClass());

    private boolean includeQueryString = true;
    private boolean includeClientInfo = false;
    private boolean includeRequestHeaders = false;
    private int requestPayloadLoggingSize = 0;

    private boolean includeStatusCode = false;
    private boolean includeResponseHeaders = false;
    private int responsePayloadLoggingSize = 0;

    private BeforeRequestLoggingHandler beforeRequestHandler;
    private AfterRequestLoggingHandler afterRequestHandler;

    public static GjallConfigurer of(BeforeRequestLoggingHandler beforeRequestHandler, AfterRequestLoggingHandler afterRequestHandler) {

        if (beforeRequestHandler == null && afterRequestHandler == null) {
            throw new NullPointerException("No Request Handlers registered. At least 1 handler needed");
        }

        return new GjallConfigurer(beforeRequestHandler, afterRequestHandler);
    }

    private GjallConfigurer(BeforeRequestLoggingHandler beforeRequestHandler, AfterRequestLoggingHandler afterRequestHandler) {
        this.beforeRequestHandler = beforeRequestHandler;
        this.afterRequestHandler = afterRequestHandler;
    }

    GjallConfigurer setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
        return this;
    }

    GjallConfigurer setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
        return this;
    }

    GjallConfigurer setIncludeRequestHeaders(boolean includeRequestHeaders) {
        this.includeRequestHeaders = includeRequestHeaders;
        return this;
    }

    GjallConfigurer setRequestPayloadLoggingSize(int requestPayloadLoggingSize) {
        this.requestPayloadLoggingSize = requestPayloadLoggingSize;

        if (afterRequestHandler == null && isIncludeRequestPayload()) {
            logger.warn("No AfterRequestLoggingHandler registered. RequestPayload will ignore");
        }
        return this;
    }

    public GjallConfigurer setIncludeStatusCode(boolean includeStatusCode) {
        this.includeStatusCode = includeStatusCode;
        return this;
    }

    GjallConfigurer setIncludeResponseHeaders(boolean includeResponseHeaders) {
        this.includeResponseHeaders = includeResponseHeaders;

        if (afterRequestHandler == null && isIncludeRequestPayload()) {
            logger.warn("No AfterRequestLoggingHandler registered. ResponseHeaders will ignore");
        }
        return this;
    }

    GjallConfigurer setResponsePayloadLoggingSize(int responsePayloadLoggingSize) {
        this.responsePayloadLoggingSize = responsePayloadLoggingSize;

        if (afterRequestHandler == null && isIncludeRequestPayload()) {
            logger.warn("No AfterRequestLoggingHandler registered. ResponsePayload will ignore");
        }
        return this;
    }

    public boolean isIncludeQueryString() {
        return includeQueryString;
    }

    public boolean isIncludeClientInfo() {
        return includeClientInfo;
    }

    public boolean isIncludeRequestHeaders() {
        return includeRequestHeaders;
    }

    public int getRequestPayloadLoggingSize() {
        return requestPayloadLoggingSize;
    }

    public boolean isIncludeRequestPayload() {
        return requestPayloadLoggingSize > 0;
    }

    public boolean isIncludeStatusCode() {
        return includeStatusCode;
    }

    public boolean isIncludeResponseHeaders() {
        return includeResponseHeaders;
    }

    public int getResponsePayloadLoggingSize() {
        return responsePayloadLoggingSize;
    }

    public boolean isIncludeResponsePayload() {
        return responsePayloadLoggingSize > 0;
    }

    public BeforeRequestLoggingHandler getBeforeRequestHandler() {
        return beforeRequestHandler;
    }

    public AfterRequestLoggingHandler getAfterRequestHandler() {
        return afterRequestHandler;
    }

    public boolean isIncludeResponseLog() {
        return isIncludeStatusCode() || isIncludeResponseHeaders() || isIncludeResponsePayload();
    }
}
