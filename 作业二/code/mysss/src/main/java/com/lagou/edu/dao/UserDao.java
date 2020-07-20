package com.lagou.edu.dao;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 用户持久层
 */
public interface UserDao extends JpaRepository<User,Long>, JpaSpecificationExecutor<Resume> {

}
