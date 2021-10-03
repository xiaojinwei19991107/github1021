package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelgroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * TravelgroupController
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-25
 * @Description:
 */
@RestController
@RequestMapping("/travelgroup")
public class TravelgroupController {

    @Reference
    TravelgroupService travelgroupService;

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<TravelItem> list = travelgroupService.findAll();
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelgroupService.findPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELGROUP_ADD')")
    public Result add(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
        try {
            travelgroupService.add(travelGroup,travelItemIds);
            return new Result(true,MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELGROUP_DELETE')")
    public Result delete(Integer id){
        try {
            travelgroupService.delete(id);
            return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/getById")
    public Result getById(Integer id){
        try {
            TravelGroup travelGroup = travelgroupService.getById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/getitemidsBygroupid")
    public Result getitemidsBygroupid(Integer id){
        try {
            List<Integer> list = travelgroupService.getitemidsBygroupid(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELGROUP_EDIT')")
    public Result edit(Integer[] travelItemIds,@RequestBody TravelGroup travelGroup){
        try {
            travelgroupService.edit(travelItemIds,travelGroup);
            return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/findAllgroup")
    public Result findAllgroup(){
        try {
            List<TravelGroup> list = travelgroupService.findAllgroup();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }
}