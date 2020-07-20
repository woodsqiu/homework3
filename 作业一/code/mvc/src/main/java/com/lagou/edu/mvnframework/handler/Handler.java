package com.lagou.edu.mvnframework.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author wangzhiqiu
 * @since 2020-07-15 20:53
 */
public class Handler {

    private Object controller; // controller对象

    private Method method; // 方法

    private Pattern pattern; // url Pattern

    private Map<String, Integer> paramIndexMapping; // 参数和索引位置映射

    private String[] authArray; // 可以访问当前handler方法的用户集合

    public Handler(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        paramIndexMapping = new HashMap<>();
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Map<String, Integer> getParamIndexMapping() {
        return paramIndexMapping;
    }

    public void setParamIndexMapping(Map<String, Integer> paramIndexMapping) {
        this.paramIndexMapping = paramIndexMapping;
    }

    public String[] getAuthArray() {
        return authArray;
    }

    public void setAuthArray(String[] authArray) {
        this.authArray = authArray;
    }
}
