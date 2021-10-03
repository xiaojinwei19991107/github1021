package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrdersettingService;
import com.atguigu.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * OrdersettingServiceImpl
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-28
 * @Description:
 */
@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingDao ordersettingDao;


    @Override
    public void upload(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            int count = ordersettingDao.findOrderSettingByOrderDate(orderSetting.getOrderDate());
            if(count > 0){
                ordersettingDao.update(orderSetting);
            }else{
                ordersettingDao.upload(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String startDate, String endDate) {
        Map map1 = new HashMap();
        map1.put("startDate",startDate);
        map1.put("endDate",endDate);
        List<Map> list = new ArrayList<>();
        List<OrderSetting> orderSettings = ordersettingDao.getOrderSettingByMonth(map1);
        for (OrderSetting orderSetting : orderSettings) {
            Map map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            list.add(map);
        }
        return list;
    }

    @Override
    public int findOrderSettingByOrderDate(Date orderDate) {
        return ordersettingDao.findOrderSettingByOrderDate(orderDate);
    }

    @Override
    public void updateNumber(OrderSetting orderSetting) {
        ordersettingDao.updateNumber(orderSetting);
    }

    @Override
    public void addOrderSetting(OrderSetting orderSetting) {
        ordersettingDao.upload(orderSetting);
    }
}