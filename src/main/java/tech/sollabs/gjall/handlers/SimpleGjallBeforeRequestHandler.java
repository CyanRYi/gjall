package tech.sollabs.gjall.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tech.sollabs.gjall.ApiLog;
import tech.sollabs.gjall.handlers.core.GjallBeforeRequestHandler;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see GjallBeforeRequestHandler
 */
public class SimpleGjallBeforeRequestHandler implements GjallBeforeRequestHandler {

    protected final Log logger = LogFactory.getLog(getClass());

    public void handleBeforeRequest(HttpServletRequest req, ApiLog apiLog) {

        if (logger.isDebugEnabled()) {
            logger.debug(apiLog);
        }
    }
}
