package tech.sollabs.gjall.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.sollabs.gjall.configurer.GjallConfigurerAdapter;
import tech.sollabs.gjall.GjallRequestLoggingFilter;
import tech.sollabs.gjall.configurer.GjallConfigurer;
import tech.sollabs.gjall.configurer.GjallConfigurerBuilder;
import tech.sollabs.gjall.handlers.SimpleGjallAfterRequestHandler;
import tech.sollabs.gjall.handlers.SimpleGjallBeforeRequestHandler;
import tech.sollabs.gjall.handlers.core.GjallAfterRequestHandler;
import tech.sollabs.gjall.handlers.core.GjallBeforeRequestHandler;

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
 * @see GjallBeforeRequestHandler
 * @see GjallAfterRequestHandler
 */
@Configuration
public class GjallConfiguration {

    private GjallConfigurer configurer;
    private GjallConfigurerAdapter configurerAdapter;
    private GjallBeforeRequestHandler beforeRequestHandler = new SimpleGjallBeforeRequestHandler();
    private GjallAfterRequestHandler afterRequestHandler = new SimpleGjallAfterRequestHandler();

    public GjallConfiguration() {
        this.configurer = new GjallConfigurer();
    }

    @PostConstruct
    public void init() {
        if (configurerAdapter != null) {
            configurerAdapter.configure(gjallConfigurerBuilder());
        }
    }

    @Bean
    public GjallRequestLoggingFilter gjallRequestLoggingFilter() {
        return new GjallRequestLoggingFilter(configurer);
    }

    @Bean
    public GjallConfigurerBuilder gjallConfigurerBuilder() {
        GjallConfigurerBuilder configurerBuilder = new GjallConfigurerBuilder(configurer);

        configurerBuilder.beforeHandler(beforeRequestHandler);
        configurerBuilder.afterHandler(afterRequestHandler);

        return configurerBuilder;
    }

    @Autowired(required = false)
    public void setConfigurerAdapter(GjallConfigurerAdapter configurerAdapter) {
        this.configurerAdapter = configurerAdapter;
    }

    @Autowired(required = false)
    public void setBeforeRequestHandler(GjallBeforeRequestHandler beforeRequestHandler) {
        this.beforeRequestHandler = beforeRequestHandler;
    }

    @Autowired(required = false)
    public void setAfterRequestHandler(GjallAfterRequestHandler afterRequestHandler) {
        this.afterRequestHandler = afterRequestHandler;
    }
}
