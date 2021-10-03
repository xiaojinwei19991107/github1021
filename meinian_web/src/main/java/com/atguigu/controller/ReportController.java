package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.service.MemberService;
import com.atguigu.service.OrderService;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.DateUtils;
import com.atguigu.utils.POIUtils;
import javafx.beans.binding.ObjectExpression;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    MemberService memberService;
    @Reference
    SetmealService setmealService;
    @Reference
    OrderService orderService;
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        //根据前端需求，要返回应该map集合并且要包含两个数据moths={12个月} 和12个月的每月的会员数量
        Map<String,List> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        //创建日历对象将时间往前挪32个月到2019年3月份和数据库的数据对其
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH,-32);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        for(int i = 0;i<12;i++){
            //从2019年3月开始往后获取12个月
            calendar.add(Calendar.MONTH,1);//2019.4
            //获取当前日历的时间并转为S听类型的日期类
            String format = simpleDateFormat.format(calendar.getTime());
            //加入list集合
            list.add(format);
        }
        //通过用户注册获取指定时间内所有的会员数量
        List<Integer> memberList = memberService.findCountByRegTime(list);
        map.put("months",list);
        map.put("memberCount",memberList);
        return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        Map<String,List> map = new HashMap();
        List<Map> setmealCount = setmealService.getSetmealAndValue();
        List setmealNames = new ArrayList();
        for (Map map1 : setmealCount) {
            String name = (String)map1.get("name");
            Object value = map1.get("value");
            setmealNames.add(name);
        }
        map.put("setmealNames",setmealNames);
        map.put("setmealCount",setmealCount);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date reportDate = DateUtils.getToday();
        String format = simpleDateFormat.format(reportDate);
        Date date = simpleDateFormat.parse(format);
        int todayNewMember = memberService.findNewMemberByToday(date);
        int totalMember = memberService.findAll();
        Date thisWeekMonday = DateUtils.getThisWeekMonday();
        int thisWeekNewMember = memberService.findthisWeekNewMember(thisWeekMonday);
        Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
        int thisMonthNewMember = memberService.findthisMonthNewMember(firstDay4ThisMonth);
        int todayOrderNumber = orderService.findtodayOrderNumber(date);
        int todayVisitsNumber = orderService.findtodayVisitsNumber(date);
        int thisWeekOrderNumber = orderService.findthisWeekOrderNumber(thisWeekMonday);
        int thisWeekVisitsNumber = orderService.findthisWeekVisitsNumber(thisWeekMonday);
        int thisMonthOrderNumber = orderService.findthisMonthOrderNumber(firstDay4ThisMonth);
        int thisMonthVisitsNumber = orderService.findthisMonthVisitsNumber(firstDay4ThisMonth);
        List<Map> hotSetmeal = orderService.findhotSetmeal();
        Map map = new HashMap();
        map.put("reportDate",date);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotSetmeal",hotSetmeal);
        return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }
    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取模板文件的路径
        String templatePath =  request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
        Result businessReportData = getBusinessReportData();
        Map data = (Map)businessReportData.getData();


        String reportDate = (String) data.get("reportDate");
        Integer todayNewMember = (Integer) data.get("todayNewMember");
        Integer totalMember = (Integer) data.get("totalMember");
        Integer thisWeekNewMember = (Integer) data.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) data.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) data.get("todayOrderNumber");
        Integer todayVisitsNumber = (Integer) data.get("todayVisitsNumber");
        Integer thisWeekOrderNumber = (Integer) data.get("thisWeekOrderNumber");
        Integer thisWeekVisitsNumber = (Integer) data.get("thisWeekVisitsNumber");
        Integer thisMonthOrderNumber = (Integer) data.get("thisMonthOrderNumber");
        Integer thisMonthVisitsNumber = (Integer) data.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) data.get("hotSetmeal");

        // 获取excel的表格对象
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templatePath)));
        XSSFSheet sheet = workbook.getSheetAt(0);
        // 往行里面添加数据 3 -- >2
        XSSFRow row = sheet.getRow(2);
        // 往列里面添加数据
        // 添加日期数据
        row.getCell(5).setCellValue(reportDate);

        // 获取第四行，往第四行里面写数据
        row = sheet.getRow(4);
        //  新增会员数
        row.getCell(5).setCellValue(todayNewMember);
        // 表示总会员数
        row.getCell(7).setCellValue(totalMember);

        row = sheet.getRow(5);
        // 本周新增会员数
        row.getCell(5).setCellValue(thisWeekNewMember);
        // 本月新增会员数
        row.getCell(7).setCellValue(thisMonthNewMember);

        row = sheet.getRow(7);
        row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
        row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

        row = sheet.getRow(8);
        row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
        row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

        row = sheet.getRow(9);
        row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
        row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

        int rowNum = 12;
        for(Map map1 : hotSetmeal){//热门套餐
            String name = (String) map1.get("name");
            Long setmeal_count = (Long) map1.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map1.get("proportion");
            row = sheet.getRow(rowNum ++);
            row.getCell(4).setCellValue(name);//套餐名称
            row.getCell(5).setCellValue(setmeal_count);//预约数量
            row.getCell(6).setCellValue(proportion.doubleValue());//占比
        }

        //  输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 下载数据类型(excel类型)
        response.setContentType("application/vnd.ms-excel");
        // 设置下载的形式，进行下载
        response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
        // 写数据
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }
}
