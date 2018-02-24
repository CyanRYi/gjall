package tech.sollabs.gjall.configurer;

/**
 * extend to configure gjall advanced configuration via Java code
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see GjallConfigurerBuilder
 */
public abstract class GjallConfigurerAdapter {

    public abstract void configure(GjallConfigurerBuilder gjall);
}
