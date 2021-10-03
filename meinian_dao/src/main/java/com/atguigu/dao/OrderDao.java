package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrderDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-31
 * @Description:
 */
public interface OrderDao {

    Order findOrderByOrderDateAndid(Map map1);

    void saveOrder(Order order);

    Map<String, Object> findByid(Integer orderId);

    int findtodayOrderNumber(Date reportDate);

    int findtodayVisitsNumber(Date reportDate);

    int findthisWeekOrderNumber(Date thisWeekMonday);

    int findthisWeekVisitsNumber(Date thisWeekMonday);

    int findthisMonthOrderNumber(Date firstDay4ThisMonth);

    int findthisMonthVisitsNumber(Date firstDay4ThisMonth);

    List<Map> findhotSetmeal();
}

