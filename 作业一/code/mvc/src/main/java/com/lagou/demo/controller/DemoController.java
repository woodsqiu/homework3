package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvnframework.annotation.LagouAutowired;
import com.lagou.edu.mvnframework.annotation.LagouController;
import com.lagou.edu.mvnframework.annotation.LagouRequestMapping;
import com.lagou.edu.mvnframework.annotation.LagouSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 不需要权限校验的controller
 *
 * @author wangzhiqiu
 * @since 2020-07-14 23:06
 */
@LagouController
@LagouRequestMapping("/demo")
public class DemoController {

    @LagouAutowired
    private IDemoService demoService;

    @LagouRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String username){
        return demoService.getName(username);
    }
}
