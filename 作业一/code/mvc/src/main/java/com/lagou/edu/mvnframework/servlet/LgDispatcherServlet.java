package com.lagou.edu.mvnframework.servlet;

import com.lagou.edu.mvnframework.annotation.*;
import com.lagou.edu.mvnframework.handler.Handler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangzhiqiu
 * @since 2020-07-14 21:08
 */
public class LgDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    // 缓存扫描到的全限定类名
    private List<String> classNames = new ArrayList<>();

    // ioc
    private Map<String, Object> ioc = new HashMap<>();


    private List<Handler> handlerMapping = new ArrayList<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1 加载配置文件 springmvc.properties
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        doLoadConfig(contextConfigLocation);


        // 2 扫描包，注解
        doScan(properties.getProperty("scanPackage"));


        // 3 初始化bean（基于ioc，基于注解）
        doInstance();

        // 4 实现依赖注入
        doAutowired();


        // 5 构造一个handleMapping,将配置好的url和method建立关系
        initHandlerMapping();


        System.out.println("springmvc 初始化完成");
        // 等待请求进入
    }

    // 构造一个handleMapping,将配置好的url和method建立关系
    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();
            if (!aClass.isAnnotationPresent(LagouController.class)) {
                continue;
            }

            // 类上的url
            String baseUrl = "";
            if (aClass.isAnnotationPresent(LagouRequestMapping.class)) {
                LagouRequestMapping annotation = aClass.getAnnotation(LagouRequestMapping.class);
                baseUrl = annotation.value();
            }

            // 遍历方法
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(LagouRequestMapping.class)) {
                    continue;
                }
                LagouRequestMapping annotation = method.getAnnotation(LagouRequestMapping.class);
                String methodUrl = annotation.value();
                String url = baseUrl + methodUrl;

                Handler handler = new Handler(entry.getValue(), method, Pattern.compile(url));

                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    // 如果是HttpServletResponse或HttpServletRequest,取类型名称
                    if (parameter.getType() == HttpServletResponse.class || parameter.getType() == HttpServletRequest.class) {
                        handler.getParamIndexMapping().put(parameter.getType().getName(), i);
                    } else {
                        handler.getParamIndexMapping().put(parameter.getName(), i);
                    }
                }

                handlerMapping.add(handler);
            }
        }

        // 根据security注解设置访问权限
        authenticationSetting();
    }

    // 根据security注解设置访问权限
    private void authenticationSetting() {
        for (Handler handler : handlerMapping) {
            Class<?> aClass = handler.getController().getClass();
            String[] authArray = null;
            if (aClass.isAnnotationPresent(LagouSecurity.class)) {
                LagouSecurity annotation = aClass.getAnnotation(LagouSecurity.class);
                authArray = annotation.value();
            }
            Method method = handler.getMethod();
            if (method.isAnnotationPresent(LagouSecurity.class)) {
                LagouSecurity annotation = method.getAnnotation(LagouSecurity.class);
                authArray = annotation.value();
            }
            // 如果controller或者方法上设置了security访问权限，则在handlerMapping的handler中，将有权限的用户保存到authSet集合
            if (authArray != null) {
                handler.setAuthArray(authArray);
            }
        }
    }

    // 实现依赖注入
    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!declaredField.isAnnotationPresent(LagouAutowired.class)) {
                    return;
                }
                LagouAutowired annotation = declaredField.getAnnotation(LagouAutowired.class);
                String beanName = annotation.value();
                if ("".equals(beanName.trim())) {
                    // 没有指定beanId，根据当前字段类型（接口注入） IDemoService
                    beanName = declaredField.getType().getName();
                }
                // 开启赋值
                declaredField.setAccessible(true);
                try {
                    declaredField.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 初始化bean（基于ioc，基于注解）
    private void doInstance() {
        if (classNames.size() == 0) {
            return;
        }
        try {
            for (int i = 0; i < classNames.size(); i++) {
                String className = classNames.get(i); // com.lagou.demo.controller.DemoController
                Class<?> aClass = Class.forName(className);
                // 区分controller/service
                if (aClass.isAnnotationPresent(LagouController.class)) {
                    String simpleName = aClass.getSimpleName();
                    String beanName = lowerFirst(simpleName);
                    Object o = aClass.newInstance();
                    ioc.put(beanName, o);
                } else if (aClass.isAnnotationPresent(LagouService.class)) {
                    LagouService annotation = aClass.getAnnotation(LagouService.class);
                    String beanName = annotation.value();
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirst(aClass.getSimpleName());
                    }
                    ioc.put(beanName, aClass.newInstance());
                    // service层有实现接口的，将接口对象放入一份到ioc中
                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (int j = 0; j < interfaces.length; j++) {
                        Class<?> anInterface = interfaces[j];
                        // 以接口的全限定类型作为id放入
                        ioc.put(anInterface.getName(), aClass.newInstance());
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.copyValueOf(chars);
    }


    // 扫描包，注解
    // com.lagou.demo
    private void doScan(String scanPackage) {
        String scanPackagePath =
                Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "/");
        File pack = new File(scanPackagePath);
        File[] files = pack.listFiles();
        for (File file : files) {
            if (file.isDirectory()) { // 子package com.lagou.demo.controller
                doScan(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = scanPackage + "." + file.getName().replaceAll("\\.class", "");
                classNames.add(className);
            }
        }
    }

    // 加载配置文件 springmvc.properties
    private void doLoadConfig(String contextConfigLocation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 接收请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    // 处理请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Handler handler = getHandler(req);
        if (handler == null) {
            resp.getWriter().write("404 not found");
            return;
        }
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();

        Object[] paramValues = new Object[parameterTypes.length];


        // 绑定参数
        Map<String, String[]> parameterMap = req.getParameterMap();


        // 如果权限校验不通过，返回403错误
        if (!isAuth(handler, parameterMap)) {
            resp.getWriter().write("403 authentication failed");
            return;
        }


        // 向参数中塞值，保证参数顺序和方法中形参顺序相同
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String value = StringUtils.join(entry.getValue(), ",");
            if (!handler.getParamIndexMapping().containsKey(entry.getKey())) {
                continue;
            }
            Integer index = handler.getParamIndexMapping().get(entry.getKey());
            paramValues[index] = value;
        }

        if (handler.getParamIndexMapping().containsKey(HttpServletRequest.class.getSimpleName())) {
            Integer reqIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getSimpleName());
            paramValues[reqIndex] = req;
        }

        if (handler.getParamIndexMapping().containsKey(HttpServletResponse.class.getSimpleName())) {
            Integer respIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getSimpleName());
            paramValues[respIndex] = resp;
        }


        try {
            handler.getMethod().invoke(handler.getController(), paramValues);
            resp.getWriter().write("业务逻辑执行完成");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 权限校验
    private boolean isAuth(Handler handler, Map<String, String[]> parameterMap) {
        System.out.println("check authentication start");
        String[] authArray = handler.getAuthArray();
        // 如果认证数组为空，则代表没有设置访问权限，直接放行
        if (authArray == null) {
            return true;
        }
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            // 如果入参不是username,不处理
            if (!"username".equals(key)) {
                continue;
            }
            // 如果没有没有传入用户名，直接返回认证失败
            String[] value = entry.getValue();
            if (value == null) {
                return false;
            }
            // 如果用户在认证数组中，则放行
            for (String username : value) {
                if (ArrayUtils.contains(authArray, username)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Handler getHandler(HttpServletRequest req) {
        if (handlerMapping.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        for (Handler handler : handlerMapping) {
            Matcher matcher = handler.getPattern().matcher(url);
            if (!matcher.matches()) {
                continue;
            }
            return handler;
        }
        return null;
    }
}
