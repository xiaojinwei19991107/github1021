package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrdersettingService;
import com.atguigu.utils.POIUtils;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.apache.taglibs.standard.tag.common.core.RedirectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrdersettingController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-28
 * @Description:
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> rows = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] row : rows) {
                OrderSetting orderSetting = new OrderSetting();
                orderSetting.setOrderDate(new Date(row[0]));
                orderSetting.setNumber(Integer.parseInt(row[1]));
                orderSettingList.add(orderSetting);
            }
            ordersettingService.upload(orderSettingList);
            return new Result(true,MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        String startDate = date + "-1";
        String endDate = date + "-31";
        List<Map> list = ordersettingService.getOrderSettingByMonth(startDate,endDate);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,list);
    }
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){//自动将字符串形式的日期转换成日期格式加入orderSetting
        try {
            int count = ordersettingService.findOrderSettingByOrderDate(orderSetting.getOrderDate());
            if(count > 0){
                ordersettingService.updateNumber(orderSetting);
            }else{
                ordersettingService.addOrderSetting(orderSetting);
            }
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ORDERSETTING_FAIL);
        }
    }

}