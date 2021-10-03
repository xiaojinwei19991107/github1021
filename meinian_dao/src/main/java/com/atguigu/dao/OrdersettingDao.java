package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrdersettingDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-28
 * @Description:
 */
public interface OrdersettingDao {

    void upload(OrderSetting orderSetting);

    int findOrderSettingByOrderDate(Date orderDate);

    void update(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    void updateNumber(OrderSetting orderSetting);

    OrderSetting finresverationByprderDate(Date orderDate);

    void updateReservation(OrderSetting orderSetting);
}

