package com.atguigu.dao;

import com.atguigu.pojo.Role;

import java.util.Set;

/**
 * RoleDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-04-01
 * @Description:
 */
public interface RoleDao {
    Set<Role> findRolesByUserid(Integer id);
}

