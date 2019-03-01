package tech.sollabs.gjall.configurer;

import org.springframework.util.Assert;
import tech.sollabs.gjall.handlers.AfterRequestLoggingHandler;
import tech.sollabs.gjall.handlers.BeforeRequestLoggingHandler;

/**
 * Builder to make GjallConfigurer
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see tech.sollabs.gjall.GjallRequestLoggingFilter
 */
public class GjallConfigurerBuilder {

    private boolean includeQueryString = true;
    private boolean includeClientInfo = false;
    private BeforeRequestLoggingHandler beforeRequestHandler;
    private AfterRequestLoggingHandler afterRequestHandler;
    private GjallRequestConfigurer requestConfigurer = new GjallRequestConfigurer();
    private GjallResponseConfigurer responseConfigurer = new GjallResponseConfigurer();

    public GjallConfigurerBuilder includeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
        return this;
    }

    public GjallConfigurerBuilder includeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
        return this;
    }

    public GjallConfigurerBuilder beforeHandler(BeforeRequestLoggingHandler beforeHandler) {
        this.beforeRequestHandler = beforeHandler;
        return this;
    }

    public GjallConfigurerBuilder afterHandler(AfterRequestLoggingHandler afterHandler) {
        this.afterRequestHandler = afterHandler;
        return this;
    }

    public GjallRequestConfigurer request() { return this.requestConfigurer; }

    public GjallResponseConfigurer response() { return this.responseConfigurer; }

    public class GjallRequestConfigurer {

        boolean includeHeaders = false;
        int payloadSize = 0;

        public GjallRequestConfigurer includeHeaders(boolean includeHeaders) {
            this.includeHeaders = includeHeaders;
            return this;
        }

        public GjallRequestConfigurer payloadSize(int payloadSize) {

            Assert.isTrue(payloadSize > -1, "Payload size cannot be negative. If you want ignore payload, insert 0 instead negative integer.");
            Assert.isTrue(payloadSize <= 5000, "Payload size cannot be over 5000. It allows from 0 to 5000.(0 means ignore payload)");

            this.payloadSize = payloadSize;
            return this;
        }

        public GjallConfigurerBuilder and() {
            return GjallConfigurerBuilder.this;
        }
    }

    public class GjallResponseConfigurer {

        boolean includeHeaders = false;
        int payloadSize = 0;

        public GjallResponseConfigurer includeHeaders(boolean includeHeader) {
            this.includeHeaders = includeHeader;
            return this;
        }

        public GjallResponseConfigurer payloadSize(int payloadSize) {

            Assert.isTrue(payloadSize > -1, "Payload size cannot be negative. If you want ignore payload, insert 0 instead negative integer.");
            Assert.isTrue(payloadSize <= 5000, "Payload size cannot be over 5000. It allows from 0 to 5000.(0 means ignore payload)");

            this.payloadSize = payloadSize;
            return this;
        }

        public GjallConfigurerBuilder and() {
            return GjallConfigurerBuilder.this;
        }
    }

    public GjallConfigurer build() {

        return GjallConfigurer.of(beforeRequestHandler, afterRequestHandler)
                .setIncludeClientInfo(includeClientInfo)
                .setIncludeQueryString(includeQueryString)
                .setIncludeRequestHeaders(requestConfigurer.includeHeaders)
                .setRequestPayloadLoggingSize(requestConfigurer.payloadSize)
                .setIncludeResponseHeaders(responseConfigurer.includeHeaders)
                .setResponsePayloadLoggingSize(responseConfigurer.payloadSize);
    }
}