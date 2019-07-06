package tech.sollabs.gjall.annotation;

import org.springframework.context.annotation.Import;
import tech.sollabs.gjall.GjallConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable Gjall configuration for API logging
 *
 * @author Cyan Raphael Yi
 * @since 0.1.0
 * @see GjallConfiguration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GjallConfiguration.class)
public @interface EnableApiLogging {
}
