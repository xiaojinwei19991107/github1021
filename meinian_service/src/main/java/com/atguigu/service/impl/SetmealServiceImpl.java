package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SetmealServiceImpl
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-27
 * @Description:
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    JedisPool jedisPool;
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Setmeal setmeal, Integer[] travelgroupIds) {
        setmealDao.add(setmeal);
        Integer id = setmeal.getId();
        addsetmealAndgroup(id,travelgroupIds);
        String img = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
    }

    private void addsetmealAndgroup(Integer id, Integer[] travelgroupIds) {
        Map<String,Integer> map = new HashMap();
        for (Integer travelgroupId : travelgroupIds) {
            map.put("setmealid",id);
            map.put("groupid",travelgroupId);
            setmealDao.addsetmealAndgroup(map);
        }
    }

    @Override
    public List getSetmeal() {
        List list = setmealDao.getSetmeal();
        return list;
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public Setmeal getsetmealById(Integer id) {
        Setmeal setmeal = setmealDao.getsetmealById(id);
        return setmeal;
    }

    @Override
    public List<Map> getSetmealAndValue() {
        return setmealDao.getSetmealAndValue();
    }
}