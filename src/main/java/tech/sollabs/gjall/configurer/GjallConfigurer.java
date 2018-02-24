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

    public GjallConfigurer(boolean includeQueryString, boolean includeClientInfo, boolean includeRequestHeaders, int requestPayloadLoggingSize, boolean includeResponseHeaders, int responsePayloadLoggingSize, BeforeRequestLoggingHandler beforeRequestHandler, AfterRequestLoggingHandler afterRequestHandler) {
        this.includeQueryString = includeQueryString;
        this.includeClientInfo = includeClientInfo;
        this.includeRequestHeaders = includeRequestHeaders;
        this.requestPayloadLoggingSize = requestPayloadLoggingSize;
        this.includeResponseHeaders = includeResponseHeaders;
        this.responsePayloadLoggingSize = responsePayloadLoggingSize;
        this.beforeRequestHandler = beforeRequestHandler;
        this.afterRequestHandler = afterRequestHandler;
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
