package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;

import java.util.List;

/**
 * TravelgroupService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-25
 * @Description:
 */
public interface TravelgroupService {

    List<TravelItem> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    void delete(Integer id);

    TravelGroup getById(Integer id);

    List<Integer> getitemidsBygroupid(Integer id);

    void edit(Integer[] id,TravelGroup travelGroup);

    List<TravelGroup> findAllgroup();
}

