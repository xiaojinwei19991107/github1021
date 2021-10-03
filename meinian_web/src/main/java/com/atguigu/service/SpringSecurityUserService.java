package com.atguigu.service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Permission;
import com.atguigu.pojo.Role;
import com.atguigu.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * UserService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-04-01
 * @Description:
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.atguigu.pojo.User user = userService.findUserByUsername(s);
        if(user == null){
            return null;
        }
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                grantedAuthoritySet.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(s,user.getPassword(),grantedAuthoritySet);
        return user1;
    }
}