package tech.sollabs.gjall.configurer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tech.sollabs.gjall.handlers.AfterRequestLoggingHandler;
import tech.sollabs.gjall.handlers.BeforeRequestLoggingHandler;
import tech.sollabs.gjall.handlers.SimpleBeforeRequestLoggingHandler;

/**
 * Has config about include or exclude logging properties
 * setter can access from {@code {@link ApiLoggingConfigurerBuilder }}
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see AfterRequestLoggingHandler
 * @see BeforeRequestLoggingHandler
 * @see ApiLoggingConfigurerBuilder
 * @see tech.sollabs.gjall.GjallRequestLoggingFilter
 */
public class ApiLoggingConfigurer {

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

    public static ApiLoggingConfigurer of(BeforeRequestLoggingHandler beforeRequestHandler, AfterRequestLoggingHandler afterRequestHandler) {
        return new ApiLoggingConfigurer(beforeRequestHandler, afterRequestHandler);
    }

    private ApiLoggingConfigurer(BeforeRequestLoggingHandler beforeRequestHandler, AfterRequestLoggingHandler afterRequestHandler) {
        if (beforeRequestHandler == null && afterRequestHandler == null) {
            logger.info("No Request Handlers registered. tech.sollabs.gjall.handlers.SimpleBeforeRequestLoggingHandler will be add for default configuration");
            this.beforeRequestHandler = new SimpleBeforeRequestLoggingHandler();
        } else {
            this.beforeRequestHandler = beforeRequestHandler;
            this.afterRequestHandler = afterRequestHandler;
        }
    }

    ApiLoggingConfigurer setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
        return this;
    }

    ApiLoggingConfigurer setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
        return this;
    }

    ApiLoggingConfigurer setIncludeRequestHeaders(boolean includeRequestHeaders) {
        this.includeRequestHeaders = includeRequestHeaders;
        return this;
    }

    ApiLoggingConfigurer setRequestPayloadLoggingSize(int requestPayloadLoggingSize) {
        this.requestPayloadLoggingSize = requestPayloadLoggingSize;

        if (afterRequestHandler == null && isIncludeRequestPayload()) {
            logger.warn("No AfterRequestLoggingHandler registered. RequestPayload will ignore");
        }
        return this;
    }

    public ApiLoggingConfigurer setIncludeStatusCode(boolean includeStatusCode) {
        this.includeStatusCode = includeStatusCode;
        return this;
    }

    ApiLoggingConfigurer setIncludeResponseHeaders(boolean includeResponseHeaders) {
        this.includeResponseHeaders = includeResponseHeaders;

        if (afterRequestHandler == null && isIncludeRequestPayload()) {
            logger.warn("No AfterRequestLoggingHandler registered. ResponseHeaders will ignore");
        }
        return this;
    }

    ApiLoggingConfigurer setResponsePayloadLoggingSize(int responsePayloadLoggingSize) {
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
