package tech.sollabs.gjall.handlers.core;

import tech.sollabs.gjall.ApiLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see tech.sollabs.gjall.GjallRequestLoggingFilter
 */
public interface GjallAfterRequestHandler {

    void handleAfterRequest(HttpServletRequest req, HttpServletResponse resp, ApiLog apiLog);
}
