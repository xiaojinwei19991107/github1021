package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import com.atguigu.utils.SMSUtils;
import org.apache.xmlbeans.impl.regex.REUtil;
import org.jboss.netty.util.internal.ReusableIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * LoginController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-31
 * @Description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;
    @RequestMapping("/check")
    public Result check(HttpServletResponse httpServletResponse,@RequestBody Map map){
        try {
            String telephone = (String) map.get("telephone");
            String validateCode = (String)map.get("validateCode");
            String s = jedisPool.getResource().get(telephone+RedisMessageConstant.SENDTYPE_LOGIN);
            if(s!=null && validateCode.equals(s)){
                Member member = memberService.findBytelephone(telephone);
                if(member == null){
                    member = new Member();
                    member.setPhoneNumber(telephone);
                    member.setRegTime(new Date());
                    memberService.add(member);
                }
                Cookie cookie = new Cookie("login_member_telephone",telephone);
                cookie.setPath("/");
                cookie.setMaxAge(60*24*60*30);
                httpServletResponse.addCookie(cookie);
                return new Result(true,MessageConstant.LOGIN_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true,MessageConstant.LOGIN_FAIL);
    }
}