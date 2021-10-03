package com.atguigu.service;

import com.atguigu.entity.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrderService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-31
 * @Description:
 */
public interface OrderService {
    Result saveOrder(Map map) throws Exception;

    Map findByid(Integer orderId);

    int findtodayOrderNumber(Date reportDate);

    int findtodayVisitsNumber(Date reportDate);

    int findthisWeekOrderNumber(Date thisWeekMonday);

    int findthisWeekVisitsNumber(Date thisWeekMonday);

    int findthisMonthOrderNumber(Date firstDay4ThisMonth);

    int findthisMonthVisitsNumber(Date firstDay4ThisMonth);

    List<Map> findhotSetmeal();
}

