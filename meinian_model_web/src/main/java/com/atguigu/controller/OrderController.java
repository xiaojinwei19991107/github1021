package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import com.atguigu.utils.DateUtils;
import com.atguigu.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.management.relation.RelationSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * OrderController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-31
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    @RequestMapping("/saveOrder")
    public Result saveOrder(@RequestBody Map map){
        //获取redis中的验证码
        String s = jedisPool.getResource().get(map.get("telephone") + RedisMessageConstant.SENDTYPE_ORDER);
        if(s!=null && map.get("validateCode").equals(s)){
            try {
                Result result = orderService.saveOrder(map);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Result(false,MessageConstant.ADD_MEMBER_FAIL);
    }
    @RequestMapping("/findByid")
    public Result findByid(Integer orderId) throws Exception {
        Map map = orderService.findByid(orderId);
        Date orderDate = (Date)map.get("orderDate");
        String s = DateUtils.parseDate2String(orderDate);
        map.put("orderDate",DateUtils.parseString2Date(s));
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

}