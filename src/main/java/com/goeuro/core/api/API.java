package com.goeuro.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that an annotated class is a API command.
 *
 * Classes which marked by annotation will be initialized and stored by
 * special container class {@link com.goeuro.core.CommandContainer}.
 *
 * After initialization an instance of class it will be allow for execution by
 * {@link com.goeuro.core.CommandExecutor}
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface API {

    /**
     * Name of API command
     * @return
     */
    public String name();

    /**
     * Description of API command
     * @return
     */
    public String description();

    /**
     * Template of API command
     * @return
     */
    public String template() default "";

    /**
     * Allowed parameters of API command
     * @return
     */
    public String[] allowedParams() default "";

    /**
     * Example of API command usage
     * @return
     */
    public String example() default "";

}
