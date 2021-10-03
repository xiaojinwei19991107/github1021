package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.PageFormat;
import java.util.List;

/**
 * TravelItemController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-24
 * @Description:
 *         项目直接返回json数据
 *        @RestController = @Controller + @ResponseBody
 *        http协议code常见的错误：400：参数错误
 *                              404：请求路径错误
 *                              405：请求方式错误(前端传输的是get请求，后台controller通过post接收)
 *                              @RequestMapping：包含了所有的请求方式
 *                              @see GetMapping:对应查询
                                @see PostMapping：更新
                                @see PutMapping：增加
                                @see DeleteMapping：删除
                                500: 服务器代码错误
 *
 */
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {
    @Reference
    TravelItemService travelItemService;
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
            Result result = new Result(true,MessageConstant.ADD_TRAVELITEM_SUCCESS);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelItemService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());

        return pageResult;
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")
    public Result delete(Integer id){
        try {
            travelItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/getById")
    public Result edit(Integer id){
        try {
            TravelItem item = travelItemService.getById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,item);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")
    public Result edit(@RequestBody TravelItem travelItem){
        try {
            travelItemService.edit(travelItem);
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }
}