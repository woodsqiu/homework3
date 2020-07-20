package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvnframework.annotation.LagouAutowired;
import com.lagou.edu.mvnframework.annotation.LagouController;
import com.lagou.edu.mvnframework.annotation.LagouRequestMapping;
import com.lagou.edu.mvnframework.annotation.LagouSecurity;

/**
 * 在类上设置权限校验的controller
 *
 * @author wangzhiqiu
 * @since 2020-07-19 10:40
 */
@LagouController
@LagouRequestMapping("/methodAuth")
public class MethodAuthController {

    @LagouAutowired
    private IDemoService demoService;

    @LagouRequestMapping("/query")
    @LagouSecurity({"zhangsan","lisi"})
    public String query(String username){
        return demoService.getName(username);
    }

    @LagouRequestMapping("/find")
    @LagouSecurity({"lisi"})
    public String find(String username){
        return demoService.getName(username);
    }
}
