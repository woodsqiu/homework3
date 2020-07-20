package com.lagou.edu.controller;

import com.lagou.edu.pojo.User;
import com.lagou.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/access")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpServletResponse response, HttpSession session) throws Exception {
        System.out.println("login");
        List<User> users = userService.findUserByName(user.getName());
        for (User user1 : users) {
            if(user1.getPassword().equals(user.getPassword())){
                // 登录成功设置session属性，跳转查询页面
                session.setAttribute("loginUser", user.getName());
                return "redirect:/resume/all";
            }
        }
        session.setAttribute("loginMsg","用户名或密码错误");
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    public String registry(User user, HttpServletResponse response) throws Exception {
        System.out.println("registry");
        userService.saveUser(user);
        return "redirect:/index.jsp";
    }
}
