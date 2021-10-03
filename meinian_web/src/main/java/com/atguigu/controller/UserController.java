package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.UserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.spi.DirStateFactory;

/**
 * UserController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-04-02
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    UserService userService;
    @RequestMapping("/getusername")
    public Result getusername(){
        try {
            User user = (User) SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal();
            return new Result(true,MessageConstant.LOGIN_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.LOGIN_FAIL);
        }
    }
}