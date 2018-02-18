package tech.sollabs.gjall.handlers.core;

import tech.sollabs.gjall.ApiLog;

import javax.servlet.http.HttpServletRequest;

/**
 * API logging handler when before request.
 * this handler cannot know payload and response info - that fields are null
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see tech.sollabs.gjall.GjallRequestLoggingFilter
 * @see tech.sollabs.gjall.configurer.GjallConfigurerBuilder
 */
public interface GjallBeforeRequestHandler {

    void handleBeforeRequest(HttpServletRequest req, ApiLog apiLog);
}
