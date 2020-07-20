package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvnframework.annotation.LagouAutowired;
import com.lagou.edu.mvnframework.annotation.LagouController;
import com.lagou.edu.mvnframework.annotation.LagouRequestMapping;
import com.lagou.edu.mvnframework.annotation.LagouSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在类上设置权限校验的controller
 *
 * @author wangzhiqiu
 * @since 2020-07-19 10:36
 */
@LagouController
@LagouRequestMapping("/auth")
@LagouSecurity({"zhangsan"})
public class AuthDemoController {

    @LagouAutowired
    private IDemoService demoService;

    @LagouRequestMapping("/query")
    public String query(String username){
        return demoService.getName(username);
    }

    @LagouRequestMapping("/find")
    public String find(String username){
        return demoService.getName(username);
    }
}
