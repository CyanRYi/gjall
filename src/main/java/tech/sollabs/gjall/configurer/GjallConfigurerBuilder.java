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

    private GjallConfigurer gjallConfigurer;

    private GjallRequestConfigurer requestConfigurer = null;
    private GjallResponseConfigurer responseConfigurer = null;

    public GjallConfigurerBuilder(GjallConfigurer gjallConfigurer) {
        this.gjallConfigurer = gjallConfigurer;
    }

    public GjallConfigurerBuilder includeQueryString(boolean includeQueryString) {
        gjallConfigurer.setIncludeQueryString(includeQueryString);
        return this;
    }

    public GjallConfigurerBuilder includeClientInfo(boolean includeClientInfo) {
        gjallConfigurer.setIncludeClientInfo(includeClientInfo);
        return this;
    }

    public GjallConfigurerBuilder beforeHandler(BeforeRequestLoggingHandler beforeHandler) {
        gjallConfigurer.setBeforeRequestHandler(beforeHandler);
        return this;
    }

    public GjallConfigurerBuilder afterHandler(AfterRequestLoggingHandler afterHandler) {
        gjallConfigurer.setAfterRequestHandler(afterHandler);
        return this;
    }

    public GjallRequestConfigurer request() {

        if (requestConfigurer == null) {
            this.requestConfigurer = new GjallRequestConfigurer();
        }
        return this.requestConfigurer;
    }

    public GjallResponseConfigurer response() {

        if (responseConfigurer == null) {
            this.responseConfigurer = new GjallResponseConfigurer();
            gjallConfigurer.setIncludeResponseLog(true);
        }
        return this.responseConfigurer;
    }

    public class GjallRequestConfigurer {

        public GjallRequestConfigurer includeHeaders(boolean includeHeaders) {
            gjallConfigurer.setIncludeRequestHeaders(includeHeaders);
            return this;
        }

        public GjallRequestConfigurer payloadSize(int payloadSize) {

            Assert.isTrue(payloadSize > -1, "Payload size cannot be negative. If you want ignore payload, insert 0 instead negative integer.");
            Assert.isTrue(payloadSize <= 5000, "Payload size cannot be over 5000. It allows from 0 to 5000.(0 means ignore payload)");

            gjallConfigurer.setRequestPayloadLoggingSize(payloadSize);
            return this;
        }

        public GjallConfigurerBuilder and() {
            return GjallConfigurerBuilder.this;
        }
    }

    public class GjallResponseConfigurer {

        public GjallResponseConfigurer includeHeaders(boolean includeHeader) {
            gjallConfigurer.setIncludeResponseHeaders(includeHeader);
            return this;
        }

        public GjallResponseConfigurer payloadSize(int payloadSize) {

            Assert.isTrue(payloadSize > -1, "Payload size cannot be negative. If you want ignore payload, insert 0 instead negative integer.");
            Assert.isTrue(payloadSize <= 5000, "Payload size cannot be over 5000. It allows from 0 to 5000.(0 means ignore payload)");

            gjallConfigurer.setResponsePayloadLoggingSize(payloadSize);
            return this;
        }

        public GjallConfigurerBuilder and() {
            return GjallConfigurerBuilder.this;
        }
    }
}