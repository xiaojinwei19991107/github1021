package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.utils.HttpUtils;
import com.atguigu.utils.SMSUtils;
import com.atguigu.utils.ValidateCodeUtils;
import org.jboss.netty.util.internal.ReusableIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * ValidateCodeController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-30
 * @Description:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        try {
            String param = ValidateCodeUtils.generateValidateCode4String(4);
            SMSUtils.sendShortMessage(telephone,param);
            jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_ORDER,5*60,"1234");
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}