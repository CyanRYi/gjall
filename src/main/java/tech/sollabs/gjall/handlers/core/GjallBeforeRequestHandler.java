package tech.sollabs.gjall.handlers.core;

import tech.sollabs.gjall.ApiLog;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see tech.sollabs.gjall.GjallRequestLoggingFilter
 */
public interface GjallBeforeRequestHandler {

    void handleBeforeRequest(HttpServletRequest req, ApiLog apiLog);
}
