package tech.sollabs.gjall;

import org.springframework.util.Assert;
import tech.sollabs.gjall.configurer.GjallConfigurerBuilder;

public abstract class GjallConfigurerAdapter {

    public void configure(GjallConfigurerBuilder gjall) {

        Assert.notNull(gjall, "GjallConfigurer not initialized. use @EnableGjall with @Configuration or Import GjallConfiguration");
    }
}
