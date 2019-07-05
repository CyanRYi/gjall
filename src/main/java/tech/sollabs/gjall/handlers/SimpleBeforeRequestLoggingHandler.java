package tech.sollabs.gjall.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tech.sollabs.gjall.ApiLog;

import javax.servlet.http.HttpServletRequest;

/**
 * Simple implementation pf BeforeRequestLoggingHandler
 * This class is removed when 0.5.0, but reworked 1.0.0 for default @Enable Configuration
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see BeforeRequestLoggingHandler
 */
public class SimpleBeforeRequestLoggingHandler implements BeforeRequestLoggingHandler {

    protected final Log logger = LogFactory.getLog(getClass());

    public void handleLog(HttpServletRequest req, ApiLog apiLog) {

        if (logger.isDebugEnabled()) {
            logger.debug(apiLog);
        }
    }
}
