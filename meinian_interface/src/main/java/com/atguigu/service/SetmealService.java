package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * SetmealService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-27
 * @Description:
 */
public interface SetmealService {

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Setmeal setmeal, Integer[] travelgroupIds);

    List getSetmeal();

    Setmeal findById(Integer id);

    Setmeal getsetmealById(Integer id);

    List<Map> getSetmealAndValue();
}

