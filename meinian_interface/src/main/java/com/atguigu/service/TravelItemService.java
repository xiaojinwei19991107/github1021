package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;

import java.util.List;

/**
 * TravelItemService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-24
 * @Description:
 *
 *  查看实现类 ctrl + alt + b
 */
public interface TravelItemService {

    void add(TravelItem travelItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    TravelItem getById(Integer id);

    void edit(TravelItem travelItem);
}

