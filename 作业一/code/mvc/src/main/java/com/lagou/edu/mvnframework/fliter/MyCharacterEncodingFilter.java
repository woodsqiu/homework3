package com.lagou.edu.mvnframework.fliter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 字符编码过滤器
 *
 * @author wangzhiqiu
 * @since 2020-07-19 12:20
 */
public class MyCharacterEncodingFilter implements Filter {

    //初始化:web服务器启动时被初始化，随时等待过滤对象出现
    public void init(FilterConfig filterConfig) throws ServletException{
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");

        //放行请求，如果不写就被拦截在这里
        chain.doFilter(request, response);
    }

    //销毁：web服务器关闭时，才会被销毁
    public void destroy(){
    }
}