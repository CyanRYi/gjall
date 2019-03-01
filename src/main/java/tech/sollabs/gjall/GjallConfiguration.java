package tech.sollabs.gjall;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.sollabs.gjall.annotation.EnableGjall;
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

    private GjallConfigurerBuilder configurerBuilder = new GjallConfigurerBuilder();
    private BeforeRequestLoggingHandler beforeRequestHandler;
    private AfterRequestLoggingHandler afterRequestHandler;

    @Bean
    public GjallRequestLoggingFilter gjallRequestLoggingFilter() {

        configurerBuilder.beforeHandler(beforeRequestHandler)
                .afterHandler(afterRequestHandler);

        return new GjallRequestLoggingFilter(configurerBuilder.build());
    }

    @Autowired(required = false)
    public void setConfigurerAdapter(GjallConfigurerAdapter configurerAdapter) {

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
