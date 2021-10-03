package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * ClearImgJob
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-27
 * @Description:
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 凌晨2点清理图片
     *
     */
    public void  clearImg(){
        // 返回差集,值多的
        Set<String> set =  jedisPool.getResource().sdiff(
                RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES
        );
        Iterator<String> iterator = set.iterator();
        // 迭代删除多出来的垃圾图片
        while (iterator.hasNext()){
            // 多出来的图片
            String pic = iterator.next();
            System.out.println("删除图片的名字==="+pic);
            // 通过七牛云删除图片
            QiniuUtils.deleteFileFromQiniu(pic);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }

    }
}