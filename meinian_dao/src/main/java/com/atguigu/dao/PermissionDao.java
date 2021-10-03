package com.atguigu.dao;

import com.atguigu.pojo.Permission;

import java.util.Set;

/**
 * PermissionDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-04-01
 * @Description:
 */
public interface PermissionDao {
    Set<Permission> findkeyword(Integer id);
}

