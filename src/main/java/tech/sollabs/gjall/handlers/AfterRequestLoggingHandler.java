package tech.sollabs.gjall.handlers;

import tech.sollabs.gjall.ApiLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API logging handler when after request.
 * note : if you don't include payload or response information, this handler method will ignore.
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see tech.sollabs.gjall.GjallRequestLoggingFilter
 * @see tech.sollabs.gjall.configurer.GjallConfigurerBuilder
 */
public interface AfterRequestLoggingHandler {

    void handleLog(HttpServletRequest req, HttpServletResponse resp, ApiLog apiLog);
}
