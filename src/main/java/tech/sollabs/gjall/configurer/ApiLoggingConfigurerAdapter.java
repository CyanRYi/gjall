package tech.sollabs.gjall.configurer;

/**
 * extend to configure api logging advanced configuration via Java code
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see ApiLoggingConfigurerBuilder
 */
public abstract class ApiLoggingConfigurerAdapter {

    public abstract void configure(ApiLoggingConfigurerBuilder configurerBuilder);
}
