package tech.sollabs.gjall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.sollabs.gjall.annotation.EnableApiLogging;
import tech.sollabs.gjall.configurer.ApiLoggingConfigurerAdapter;
import tech.sollabs.gjall.configurer.ApiLoggingConfigurer;
import tech.sollabs.gjall.configurer.ApiLoggingConfigurerBuilder;
import tech.sollabs.gjall.handlers.AfterRequestLoggingHandler;
import tech.sollabs.gjall.handlers.BeforeRequestLoggingHandler;

/**
 * Default Gjall Configuration for @EnableApiLogging
 * ApiLoggingConfigurer just access here
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see EnableApiLogging
 * @see ApiLoggingConfigurer
 * @see ApiLoggingConfigurerBuilder
 * @see BeforeRequestLoggingHandler
 * @see AfterRequestLoggingHandler
 */
@Configuration
public class GjallConfiguration {

    private ApiLoggingConfigurerBuilder configurerBuilder = new ApiLoggingConfigurerBuilder();
    private BeforeRequestLoggingHandler beforeRequestHandler;
    private AfterRequestLoggingHandler afterRequestHandler;

    @Bean
    public GjallRequestLoggingFilter gjallRequestLoggingFilter() {

        configurerBuilder.beforeHandler(beforeRequestHandler)
                .afterHandler(afterRequestHandler);

        return new GjallRequestLoggingFilter(configurerBuilder.build());
    }

    @Autowired(required = false)
    public void setConfigurerAdapter(ApiLoggingConfigurerAdapter configurerAdapter) {

        if (configurerAdapter != null) {
            configurerAdapter.configure(configurerBuilder);
        }
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
