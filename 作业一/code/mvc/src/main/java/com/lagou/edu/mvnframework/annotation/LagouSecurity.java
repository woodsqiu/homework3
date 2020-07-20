package com.lagou.edu.mvnframework.annotation;

import java.lang.annotation.*;

/**
 * 安全认证注解
 *
 * @author wangzhiqiu
 * @since 2020-07-14 22:48
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LagouSecurity {
    String[] value();
}
