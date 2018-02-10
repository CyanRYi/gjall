package tech.sollabs.gjall.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.sollabs.gjall.GjallConfigurerAdapter;
import tech.sollabs.gjall.GjallRequestLoggingFilter;
import tech.sollabs.gjall.configurer.GjallConfigurer;
import tech.sollabs.gjall.configurer.GjallConfigurerBuilder;

import javax.annotation.PostConstruct;

@Configuration
public class GjallConfiguration {

    private GjallConfigurerAdapter configurerAdapter;

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
        GjallConfigurerBuilder builder = new GjallConfigurerBuilder();
        builder.setGjallConfigurer(gjallConfigurer());
        return builder;
    }

    @Autowired(required = false)
    public void setConfigurerAdapter(GjallConfigurerAdapter configurerAdapter) {
        this.configurerAdapter = configurerAdapter;
    }
}
