package tech.sollabs.gjall;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;
import tech.sollabs.gjall.configurer.ApiLoggingConfigurer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

/**
 * extends AbstractRequestLoggingFilter to Add Filter to servlet container
 * handle ApiLog(Before and After) doing own jobs via [Before/After]RequestLoggingHandler
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see ApiLog
 * @see AbstractRequestLoggingFilter
 * @see ContentCachingRequestWrapper
 * @see ContentCachingResponseWrapper
 */
public class GjallRequestLoggingFilter extends AbstractRequestLoggingFilter {

    private final ApiLoggingConfigurer configurer;

    public GjallRequestLoggingFilter(ApiLoggingConfigurer configurer) {
        this.configurer = configurer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        HttpServletResponse responseToUse = response;

        if (isFirstRequest && configurer.isIncludeRequestPayload() && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        if (isFirstRequest && configurer.isIncludeResponsePayload() && !(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }

        boolean shouldLog = shouldLog(requestToUse);
        ApiLog apiLog = new ApiLog(this.createRequestLogId());

        if (shouldLog && isFirstRequest) {
            apiLog = getBeforeLog(requestToUse, apiLog);
            beforeRequest(requestToUse, apiLog);
        }
        try {
            filterChain.doFilter(requestToUse, responseToUse);
        }
        finally {
            if (!isAsyncStarted(requestToUse) && (configurer.isIncludeResponseLog() || configurer.isIncludeRequestPayload())) {
                apiLog = getAfterLog(requestToUse, responseToUse, apiLog);
                afterRequest(requestToUse, responseToUse, apiLog);
            }
        }
    }

    private ApiLog getBeforeLog(HttpServletRequest request, ApiLog apiLog) {

        apiLog.setRequestAcceptedAt(new Date().getTime());

        apiLog.setUri(request.getRequestURI());
        apiLog.setMethod(request.getMethod());

        if (configurer.isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                apiLog.addQueryString(queryString);
            }
        }

        if (configurer.isIncludeRequestHeaders()) {
            apiLog.setRequestHeader(new ServletServerHttpRequest(request).getHeaders().toString());
        }

        if (configurer.isIncludeClientInfo()) {
            ApiLog.ClientInfo clientInfo = apiLog.getClientInfo();

            String client = request.getRemoteAddr();
            if (StringUtils.hasLength(client)) {
                clientInfo.setClient(client);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                clientInfo.setSessionId(session.getId());
            }
            String user = request.getRemoteUser();
            if (user != null) {
                clientInfo.setUser(user);
            }
        }

        return apiLog;
    }

    private ApiLog getAfterLog(HttpServletRequest request, HttpServletResponse response, ApiLog apiLog) throws IOException {

        apiLog.setRequestFinishedAt(new Date().getTime());

        if (configurer.isIncludeRequestPayload()) {

            ContentCachingRequestWrapper requestWrapper =
                    WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);

            String payload = null;

            if (requestWrapper != null) {
                byte[] buf = requestWrapper.getContentAsByteArray();
                payload = this.writeBufferAsString(
                        buf, requestWrapper.getCharacterEncoding(), configurer.getRequestPayloadLoggingSize());
            }

            apiLog.setRequestBody(payload);
        }

        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);

        if (responseWrapper == null) {
            return apiLog;
        }

        apiLog.setHttpStatus(HttpStatus.valueOf(responseWrapper.getStatusCode()));

        if (configurer.isIncludeResponseHeaders()) {
            HttpHeaders responseHeaders = new HttpHeaders();
            for (String headerName : responseWrapper.getHeaderNames()) {
                responseHeaders.add(headerName, responseWrapper.getHeader(headerName));
            }
            apiLog.setResponseHeader(responseHeaders.toString());
        }

        if (configurer.isIncludeResponsePayload()) {

            byte[] buf = responseWrapper.getContentAsByteArray();
            String payload = this.writeBufferAsString(
                    buf, responseWrapper.getCharacterEncoding(), configurer.getResponsePayloadLoggingSize());

            apiLog.setResponseBody(payload);
            responseWrapper.copyBodyToResponse();
        }

        return apiLog;
    }

    private String writeBufferAsString(byte[] buffer, String encoding, int payloadSize) {

        if (buffer.length == 0) {
            return null;
        }

        int length = Math.min(buffer.length, payloadSize);
        String payload;
        try {
            payload = new String(buffer, 0, length, encoding);
        }
        catch (UnsupportedEncodingException ex) {
            payload = "[unknown]";
        }

        return replaceEscapeWords(payload);
    }

    private String replaceEscapeWords(String payload) {
        return payload.replaceAll("\n", "")
                .replaceAll("\r", "")
                .replaceAll("\t", "");
    }

    @Deprecated
    protected void beforeRequest(HttpServletRequest httpServletRequest, String s) {
        // No More Need this.
    }

    @Deprecated
    protected void afterRequest(HttpServletRequest httpServletRequest, String s) {
        // No More Need this.
    }

    protected Object createRequestLogId() {
        return UUID.randomUUID();
    }

    private void beforeRequest(HttpServletRequest request, ApiLog apiLog) {
        if (configurer.getBeforeRequestHandler() == null) {
            return;
        }

        configurer.getBeforeRequestHandler().handleLog(request, apiLog);
    }

    private void afterRequest(HttpServletRequest request, HttpServletResponse response, ApiLog apiLog) {

        if (configurer.getAfterRequestHandler() == null) {
            return;
        }

        configurer.getAfterRequestHandler().handleLog(request, response, apiLog);
    }
}