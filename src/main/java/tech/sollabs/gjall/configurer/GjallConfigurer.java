package tech.sollabs.gjall.configurer;

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

    private boolean includeQueryString = true;
    private boolean includeClientInfo = false;

    private boolean includeRequestHeaders = false;
    private int requestPayloadLoggingSize = 0;

    private boolean includeResponseHeaders = false;
    private int responsePayloadLoggingSize = 0;

    private BeforeRequestLoggingHandler beforeRequestHandler;
    private AfterRequestLoggingHandler afterRequestHandler;

    public static GjallConfigurer of(BeforeRequestLoggingHandler beforeRequestHandler, AfterRequestLoggingHandler afterRequestHandler) {

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
        return this;
    }

    GjallConfigurer setIncludeResponseHeaders(boolean includeResponseHeaders) {
        this.includeResponseHeaders = includeResponseHeaders;
        return this;
    }

    GjallConfigurer setResponsePayloadLoggingSize(int responsePayloadLoggingSize) {
        this.responsePayloadLoggingSize = responsePayloadLoggingSize;
        return this;
    }

    public boolean isIncludeRequestPayload() {
        return requestPayloadLoggingSize > 0;
    }

    public boolean isIncludeResponsePayload() {
        return responsePayloadLoggingSize > 0;
    }

    public int getRequestPayloadLoggingSize() {
        return requestPayloadLoggingSize;
    }

    public int getResponsePayloadLoggingSize() {
        return responsePayloadLoggingSize;
    }

    public boolean isIncludeRequestHeaders() {
        return includeRequestHeaders;
    }

    public boolean isIncludeResponseHeaders() {
        return includeResponseHeaders;
    }

    public boolean isIncludeQueryString() {
        return includeQueryString;
    }

    public boolean isIncludeClientInfo() {
        return includeClientInfo;
    }

    public BeforeRequestLoggingHandler getBeforeRequestHandler() {
        return beforeRequestHandler;
    }

    public AfterRequestLoggingHandler getAfterRequestHandler() {
        return afterRequestHandler;
    }

    public boolean isIncludeResponseLog() {
        return this.isIncludeResponseHeaders() || this.isIncludeResponsePayload();
    }
}
