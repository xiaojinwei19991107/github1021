package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * SetmealController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-27
 * @Description:
 *           套餐
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Reference
    private SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.findPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i);
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString() + substring;
        QiniuUtils.upload2Qiniu(multipartFile.getBytes(),filename);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,filename);
    }
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    public Result add(Integer[] travelgroupIds,@RequestBody Setmeal setmeal){
        try {
            setmealService.add(setmeal,travelgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

}