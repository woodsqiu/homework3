package com.lagou.edu.service.impl;

import com.lagou.edu.dao.UserDao;
import com.lagou.edu.pojo.User;
import com.lagou.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangzhiqiu
 * @since 2020-07-20 01:16
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    /**
     * 查询用户
     *
     * @param name
     * @return
     */
    @Override
    public List<User> findUserByName(String name) {
        User user = new User();
        user.setName(name);
        Example<User> example = Example.of(user);
        return userDao.findAll(example);
    }
}
