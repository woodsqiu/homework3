package com.lagou.edu.mvnframework.annotation;

import java.lang.annotation.*;

/**
 * @author wangzhiqiu
 * @since 2020-07-14 22:48
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LagouService {
    String value() default "";
}
