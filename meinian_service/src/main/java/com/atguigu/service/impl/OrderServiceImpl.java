package com.atguigu.service.impl;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第一步.查询用户想预定日期的旅游是否已经满人
 * 第二步.判断当前人是否已经是会员，如果不是，则加入会员，如果是会员，接着判断当前日期的旅游该会员是否已经预约过
 * 第三步，生成订单.并订单设置已预约人数+1
 * OrderServiceImpl
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-31
 * @Description:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;


    @Override
    public Result saveOrder(Map map) throws Exception {
        //创建一个member会员对象
        Member member = new Member();
        //获取参数中的电话号码
        String telephone = (String)map.get("telephone");
        //获取预约的用户名
        String name = (String)map.get("name");
        //获取预约人的性别
        String sex = (String)map.get("sex");
        //获取预定哪个日期的套餐游
        String date = (String)map.get("orderDate");
        //用date工具类将string类型的date转换成数据包orderDate字段对应的类型
        Date orderDate = DateUtils.parseString2Date(date);
        //获取预约人的身份证号码
        String idCard = (String)map.get("idCard");
        //将预约的套餐号转变成integer类型
        Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
        //创建预约人对象
        member.setName(name);
        member.setSex(sex);
        member.setPhoneNumber(telephone);
        member.setIdCard(idCard);
        member.setRegTime(new Date());
        //根据订单日期唯一性得到当天的订单设置对象
        OrderSetting orderSettingBydate = ordersettingDao.finresverationByprderDate(orderDate);
        //判断当天已经预约的人数和最大数量对比
        if(orderSettingBydate.getReservations()>orderSettingBydate.getNumber()){
            //预约人数已经达到100。不能继续预约
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //通过电话号码查找会员
        Member count = memberDao.findmemberBytelephone(telephone);
        //如果会员不存在，创建会员并且创建订单和订单设置已预约人数+1
        if(count==null){
            //不存在当前会员，创建会员，注意这里要主键回显
            memberDao.add(member);
        }else{
            //当会员已经存在后，判断当前的预定的套餐，该会员是否已经预定过
            Map map1 = new HashMap();
            map1.put("memberid",count.getId());
            map1.put("orderDate",orderDate);
            map1.put("setmealId",setmealId);
            //通过订单的旅游日期和套餐游的id确定是否已经预约过
            Order order = orderDao.findOrderByOrderDateAndid(map1);
            if(order!=null){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        //当所有检测都过了以后，生成订单完成页面跳转
        Order order = new Order(count.getId(),orderDate,"微信支付","未出游",setmealId);
        //生成订单
        orderDao.saveOrder(order);
        //条用update更新语句将已预约人数+1
        orderSettingBydate.setReservations(orderSettingBydate.getReservations()+1);
        ordersettingDao.updateReservation(orderSettingBydate);
        //返回结果
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findByid(Integer orderId) {
        Map<String,Object> map = orderDao.findByid(orderId);
        return map;
    }

    @Override
    public int findtodayOrderNumber(Date reportDate) {
        return orderDao.findtodayOrderNumber(reportDate);
    }

    @Override
    public int findtodayVisitsNumber(Date reportDate) {
        return orderDao.findtodayVisitsNumber(reportDate);
    }

    @Override
    public int findthisWeekOrderNumber(Date thisWeekMonday) {
        return orderDao.findthisWeekOrderNumber(thisWeekMonday);
    }

    @Override
    public int findthisWeekVisitsNumber(Date thisWeekMonday) {
        return orderDao.findthisWeekVisitsNumber(thisWeekMonday);
    }

    @Override
    public int findthisMonthOrderNumber(Date firstDay4ThisMonth) {
        return orderDao.findthisMonthOrderNumber(firstDay4ThisMonth);
    }

    @Override
    public int findthisMonthVisitsNumber(Date firstDay4ThisMonth) {
        return orderDao.findthisMonthVisitsNumber(firstDay4ThisMonth);
    }

    @Override
    public List<Map> findhotSetmeal() {
        return orderDao.findhotSetmeal();
    }
}