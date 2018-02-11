package tech.sollabs.gjall.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.sollabs.gjall.GjallConfigurerAdapter;
import tech.sollabs.gjall.GjallRequestLoggingFilter;
import tech.sollabs.gjall.configurer.GjallConfigurer;
import tech.sollabs.gjall.configurer.GjallConfigurerBuilder;
import tech.sollabs.gjall.handlers.SimpleGjallAfterRequestHandler;
import tech.sollabs.gjall.handlers.SimpleGjallBeforeRequestHandler;
import tech.sollabs.gjall.handlers.core.GjallAfterRequestHandler;
import tech.sollabs.gjall.handlers.core.GjallBeforeRequestHandler;

import javax.annotation.PostConstruct;

@Configuration
public class GjallConfiguration {

    private GjallConfigurerAdapter configurerAdapter;
    private GjallBeforeRequestHandler beforeRequestHandler = new SimpleGjallBeforeRequestHandler();
    private GjallAfterRequestHandler afterRequestHandler = new SimpleGjallAfterRequestHandler();

    @PostConstruct
    public void init() {
        configurerAdapter.configure(gjallConfigurerBuilder());
    }

    @Bean
    public GjallConfigurer gjallConfigurer() {
        return new GjallConfigurer();
    }

    @Bean
    public GjallRequestLoggingFilter gjallRequestLoggingFilter() {
        return new GjallRequestLoggingFilter(gjallConfigurer());
    }

    @Bean
    public GjallConfigurerBuilder gjallConfigurerBuilder() {
        GjallConfigurerBuilder configurerBuilder = new GjallConfigurerBuilder(gjallConfigurer());

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
