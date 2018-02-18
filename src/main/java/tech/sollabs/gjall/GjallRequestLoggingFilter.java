package tech.sollabs.gjall;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;
import tech.sollabs.gjall.configurer.GjallConfigurer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * extends AbstractRequestLoggingFilter to Add Filter to servlet container
 * handle ApiLog(Before and After) doing own jobs via Gjall*RequestHandler
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see ApiLog
 * @see AbstractRequestLoggingFilter
 * @see ContentCachingRequestWrapper
 * @see ContentCachingResponseWrapper
 */
public class GjallRequestLoggingFilter extends AbstractRequestLoggingFilter {

    private final GjallConfigurer configurer;

    public GjallRequestLoggingFilter(GjallConfigurer configurer) {
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

        if (isFirstRequest && configurer.isIncludeResponseLog() && !(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }

        boolean shouldLog = shouldLog(requestToUse);
        ApiLog apiLog = new ApiLog(createRequestLogId());

        if (shouldLog && isFirstRequest) {
            apiLog = getBeforeLog(requestToUse, apiLog);
            beforeRequest(requestToUse, apiLog);
        }
        try {
            filterChain.doFilter(requestToUse, responseToUse);
        }
        finally {
            if (configurer.isEnabledAfterLog() && !isAsyncStarted(requestToUse)) {
                apiLog = getAfterLog(requestToUse, responseToUse, apiLog);
                afterRequest(requestToUse, responseToUse, apiLog);
            }
        }
    }

    private ApiLog getBeforeLog(HttpServletRequest request, ApiLog apiLog) {

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

        if (configurer.isIncludeRequestPayload()) {
            ContentCachingRequestWrapper requestWrapper =
                    WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);

            if (requestWrapper != null) {
                byte[] buf = requestWrapper.getContentAsByteArray();

                String payload = this.writeBufferAsString(
                        buf, requestWrapper.getCharacterEncoding(), configurer.getRequestPayloadLoggingSize());

                apiLog.setRequestBody(payload);
            }
        }

        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);

        apiLog.setHttpStatus(responseWrapper.getStatusCode());

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

    private String createRequestLogId() {

        SimpleDateFormat REQUEST_ID_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

        return REQUEST_ID_DATE_FORMAT.format(
                new Date()) + "_" + UUID.randomUUID().toString().substring(19).replace("-", "");
    }

    @Deprecated
    protected void beforeRequest(HttpServletRequest httpServletRequest, String s) {
        // No More Need this.
    }

    @Deprecated
    protected void afterRequest(HttpServletRequest httpServletRequest, String s) {
        // No More Need this.
    }

    private void beforeRequest(HttpServletRequest request, ApiLog apiLog) {

        configurer.getBeforeRequestHandler().handleBeforeRequest(request, apiLog);
    }

    private void afterRequest(HttpServletRequest request, HttpServletResponse response, ApiLog apiLog) {

        configurer.getAfterRequestHandler().handleAfterRequest(request, response, apiLog);
    }
}