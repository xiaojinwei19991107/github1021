package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class addressController {
    @Reference
    AddressService addressService;
    @RequestMapping("/findAllMaps")
    public Map findAllMaps(){
        Map<String,List<Map>> map = new HashMap();
        List<Address> list = addressService.findAllMaps();
        List<Map> gridnMaps = new ArrayList<>();
        List<Map> nameMaps = new ArrayList<>();
        for (Address address : list) {
            Map mapgridnMaps = new HashMap();
            Map mapnameMaps = new HashMap();
            mapgridnMaps.put("lng",address.getLng());
            mapgridnMaps.put("lat",address.getLat());
            mapnameMaps.put("addressName",address.getAddressName());
            gridnMaps.add(mapgridnMaps);
            nameMaps.add(mapnameMaps);
        }
        map.put("gridnMaps",gridnMaps);
        map.put("nameMaps",nameMaps);
        return map;
    }
    @RequestMapping("/addAddress")
    public Result addAddress(@RequestBody Address address){
        try {
            addressService.addAddress(address);
            return new Result(true, MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_ADDRESS_FAIL);
        }
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = addressService.findPage(queryPageBean);
        return pageResult;
    }
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        Address address = addressService.deleteById(id);
        return new Result(true,MessageConstant.QUERY_ADDRESS_SUCCESS,address);
    }
}
