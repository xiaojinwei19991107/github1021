package com.atguigu.service;

import com.atguigu.pojo.User;

/**
 * UserService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-04-01
 * @Description:
 */
public interface UserService {

    User findUserByUsername(String s);
}

