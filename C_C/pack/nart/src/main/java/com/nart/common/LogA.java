package com.nart.common;


import java.lang.annotation.*;

/**
 * Log annotations
 */
// Type means it can be placed on a class, method means it can be placed on a method
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogA {
    String module() default "";
    String operator() default "";
}
