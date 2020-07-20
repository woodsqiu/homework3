package com.lagou.edu.service;

import com.lagou.edu.pojo.User;

import java.util.List;

/**
 * @author wangzhiqiu
 * @since 2020-07-20 01:14
 */
public interface UserService {

    User saveUser(User user);

    List<User> findUserByName(String name);
}
