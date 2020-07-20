package com.lagou.demo.service.impl;

import com.lagou.demo.service.IDemoService;
import com.lagou.edu.mvnframework.annotation.LagouService;

/**
 * @author wangzhiqiu
 * @since 2020-07-14 23:09
 */
@LagouService("demoService")
public class DemoServiceImpl implements IDemoService {
    @Override
    public String getName(String name) {
        System.out.println("demoservice " + name);
        return "service" + name;
    }
}
