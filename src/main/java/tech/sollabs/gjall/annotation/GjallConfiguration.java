package tech.sollabs.gjall.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.sollabs.gjall.configurer.GjallConfigurerAdapter;
import tech.sollabs.gjall.GjallRequestLoggingFilter;
import tech.sollabs.gjall.configurer.GjallConfigurer;
import tech.sollabs.gjall.configurer.GjallConfigurerBuilder;
import tech.sollabs.gjall.handlers.AfterRequestLoggingHandler;
import tech.sollabs.gjall.handlers.BeforeRequestLoggingHandler;

import javax.annotation.PostConstruct;

/**
 * Default Gjall Configuration for @EnableGjall
 * GjallConfigurer just access here
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see EnableGjall
 * @see GjallConfigurer
 * @see GjallConfigurerBuilder
 * @see BeforeRequestLoggingHandler
 * @see AfterRequestLoggingHandler
 */
@Configuration
public class GjallConfiguration {

    private static Log LOGGER = LogFactory.getLog(GjallConfiguration.class);

    private GjallConfigurer configurer;
    private GjallConfigurerAdapter configurerAdapter;
    private BeforeRequestLoggingHandler beforeRequestHandler;
    private AfterRequestLoggingHandler afterRequestHandler;

    @PostConstruct
    public void init() {

        GjallConfigurerBuilder builder = new GjallConfigurerBuilder();

        builder.beforeHandler(beforeRequestHandler)
                .afterHandler(afterRequestHandler);

        if (configurerAdapter != null) {
            configurerAdapter.configure(builder);
        }

        this.configurer = builder.build();

        this.validateConfigurer();
    }

    private void validateConfigurer() {
        if (configurer.getBeforeRequestHandler() == null && configurer.getAfterRequestHandler() == null) {
            throw new NullPointerException("Gjall Request Handlers are null. At least 1 handler needed");
        }

        if (configurer.getAfterRequestHandler() == null) {
            if (configurer.isIncludeRequestPayload()) {
                LOGGER.warn("AfterRequestLoggingHandler is null. RequestPayload Logging will be ignore");
            }
            if (configurer.isIncludeResponsePayload()) {
                LOGGER.warn("AfterRequestLoggingHandler is null. ResponsePayload Logging will be ignore");
            }
            if (configurer.isIncludeResponseHeaders()) {
                LOGGER.warn("AfterRequestLoggingHandler is null. ResponseHeaders Logging will be ignore");
            }
        }
    }

    @Bean
    public GjallRequestLoggingFilter gjallRequestLoggingFilter() {
        return new GjallRequestLoggingFilter(configurer);
    }

    @Autowired(required = false)
    public void setConfigurerAdapter(GjallConfigurerAdapter configurerAdapter) {
        this.configurerAdapter = configurerAdapter;
    }

    @Autowired(required = false)
    public void setBeforeRequestHandler(BeforeRequestLoggingHandler beforeRequestHandler) {
        this.beforeRequestHandler = beforeRequestHandler;
    }

    @Autowired(required = false)
    public void setAfterRequestHandler(AfterRequestLoggingHandler afterRequestHandler) {
        this.afterRequestHandler = afterRequestHandler;
    }
}
