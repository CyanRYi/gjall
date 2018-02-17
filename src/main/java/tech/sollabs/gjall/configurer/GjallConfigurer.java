package tech.sollabs.gjall.configurer;

import tech.sollabs.gjall.handlers.core.GjallAfterRequestHandler;
import tech.sollabs.gjall.handlers.core.GjallBeforeRequestHandler;

public class GjallConfigurer {

    private boolean includeResponseLog = false;
    private int requestPayloadLoggingSize = 0;
    private int responsePayloadLoggingSize = 0;
    private boolean includeRequestHeaders = false;
    private boolean includeResponseHeaders = false;
    private boolean includeQueryString = true;
    private boolean includeClientInfo = false;
    private GjallBeforeRequestHandler beforeRequestHandler;
    private GjallAfterRequestHandler afterRequestHandler;

    public boolean isEnabledAfterLog() { return isIncludeRequestPayload() && includeResponseLog; }

    public boolean isIncludeResponseLog() {
        return includeResponseLog;
    }

    void setIncludeResponseLog(boolean includeResponseLog) {
        this.includeResponseLog = includeResponseLog;
    }

    public int getRequestPayloadLoggingSize() {
        return requestPayloadLoggingSize;
    }

    void setRequestPayloadLoggingSize(int requestPayloadLoggingSize) {
        this.requestPayloadLoggingSize = requestPayloadLoggingSize;
    }

    public int getResponsePayloadLoggingSize() {
        return responsePayloadLoggingSize;
    }

    void setResponsePayloadLoggingSize(int responsePayloadLoggingSize) {
        this.responsePayloadLoggingSize = responsePayloadLoggingSize;
    }

    public boolean isIncludeRequestHeaders() {
        return includeRequestHeaders;
    }

    void setIncludeRequestHeaders(boolean includeRequestHeaders) {
        this.includeRequestHeaders = includeRequestHeaders;
    }

    public boolean isIncludeResponseHeaders() {
        return includeResponseHeaders;
    }

    void setIncludeResponseHeaders(boolean includeResponseHeaders) {
        this.includeResponseHeaders = includeResponseHeaders;
    }

    public boolean isIncludeQueryString() {
        return includeQueryString;
    }

    void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    public boolean isIncludeClientInfo() {
        return includeClientInfo;
    }

    void setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
    }

    public GjallBeforeRequestHandler getBeforeRequestHandler() {
        return beforeRequestHandler;
    }

    void setBeforeRequestHandler(GjallBeforeRequestHandler beforeRequestHandler) {
        this.beforeRequestHandler = beforeRequestHandler;
    }

    public GjallAfterRequestHandler getAfterRequestHandler() {
        return afterRequestHandler;
    }

    void setAfterRequestHandler(GjallAfterRequestHandler afterRequestHandler) {
        this.afterRequestHandler = afterRequestHandler;
    }

    public boolean isIncludeRequestPayload() {
        return requestPayloadLoggingSize > 0;
    }

    public boolean isIncludeResponsePayload() {
        return responsePayloadLoggingSize > 0;
    }
}
