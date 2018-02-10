package tech.sollabs.gjall.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tech.sollabs.gjall.ApiLog;
import tech.sollabs.gjall.handlers.core.GjallAfterRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see GjallAfterRequestHandler
 */
public class SimpleGjallAfterRequestHandler implements GjallAfterRequestHandler {

    protected final Log logger = LogFactory.getLog(getClass());

    public void handleAfterRequest(HttpServletRequest req, HttpServletResponse resp, ApiLog apiLog) {

        if (logger.isDebugEnabled()) {
            logger.debug(apiLog);
        }
    }
}
