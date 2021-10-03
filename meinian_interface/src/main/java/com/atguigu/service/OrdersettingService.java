package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrdersettingService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-28
 * @Description:
 */
public interface OrdersettingService {


    void upload(List<OrderSetting> orderSettingList);

    List<Map> getOrderSettingByMonth(String startDate, String endDate);

    int findOrderSettingByOrderDate(Date orderDate);

    void updateNumber(OrderSetting orderSetting);

    void addOrderSetting(OrderSetting orderSetting);
}

