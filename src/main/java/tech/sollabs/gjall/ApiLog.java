package tech.sollabs.gjall;

/**
 *
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see javax.servlet.http.HttpServletRequest
 * @see javax.servlet.http.HttpServletResponse
 * @see GjallRequestLoggingFilter
 */
public class ApiLog {

    private final String id;
    private String uri;
    private String method;
    private Integer httpStatus;
    private ClientInfo clientInfo = null;
    private String requestHeader;
    private String requestBody;
    private String responseHeader;
    private String responseBody;

    public ApiLog(String id) {
        this.id = id;
    }

    public String getId() {
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

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
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

    public ApiLog setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    class ClientInfo {

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

    @Override
    public String toString() {
        return "ApiLog{" + "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", httpStatus=" + httpStatus +
                ", clientInfo=" + clientInfo +
                ", requestHeader='" + requestHeader + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", responseHeader='" + responseHeader + '\'' +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
