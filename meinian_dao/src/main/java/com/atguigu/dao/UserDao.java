package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * UserDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-04-01
 * @Description:
 */
public interface UserDao {

    User findUserByUsername(String s);
}

