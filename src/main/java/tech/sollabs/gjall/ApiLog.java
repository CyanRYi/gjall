package tech.sollabs.gjall;

import org.springframework.http.HttpStatus;

/**
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see javax.servlet.http.HttpServletRequest
 * @see javax.servlet.http.HttpServletResponse
 * @see GjallRequestLoggingFilter
 */
public class ApiLog {

    private final Object id;
    private String uri;
    private String method;
    private HttpStatus httpStatus = null;
    private ClientInfo clientInfo = null;
    private String requestHeader;
    private String requestBody;
    private String responseHeader;
    private String responseBody;
    private long requestAcceptedAt;
    private long requestFinishedAt;

    public ApiLog(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void addQueryString(String queryString) {
        this.uri += "?";
        this.uri += queryString;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ClientInfo getClientInfo() {

        if (this.clientInfo == null) {
            this.clientInfo = new ClientInfo();
        }

        return clientInfo;
    }

    public ApiLog setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
        return this;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public class ClientInfo {

        private String client;
        private String sessionId;
        private String user;

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "ClientInfo{" + "client='" + client + '\'' +
                    ", sessionId='" + sessionId + '\'' +
                    ", user='" + user + '\'' +
                    '}';
        }
    }

    public long getRequestAcceptedAt() {
        return requestAcceptedAt;
    }

    public void setRequestAcceptedAt(long requestAcceptedAt) {
        this.requestAcceptedAt = requestAcceptedAt;
    }

    public long getRequestFinishedAt() {
        return requestFinishedAt;
    }

    public void setRequestFinishedAt(long requestFinishedAt) {
        this.requestFinishedAt = requestFinishedAt;
    }

    @Override
    public String toString() {
        String sb = "ApiLog{" + "id=" + id +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", httpStatus=" + httpStatus +
                ", clientInfo=" + clientInfo +
                ", requestHeader='" + requestHeader + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", responseHeader='" + responseHeader + '\'' +
                ", responseBody='" + responseBody + '\'' +
                ", requestAcceptedAt=" + requestAcceptedAt +
                ", requestFinishedAt=" + requestFinishedAt +
                '}';
        return sb;
    }
}
