package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelgroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelgroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * TravelgroupServiceImpl
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-25
 * @Description:
 */
@Service(interfaceClass = TravelgroupService.class)
@Transactional
public class TravelgroupServiceImpl implements TravelgroupService {

    @Autowired
    TravelgroupDao travelgroupDao;
    @Override
    public List<TravelItem> findAll() {
        List<TravelItem> list = travelgroupDao.findAll();
        return list;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page = travelgroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelgroupDao.add(travelGroup);
        setTravelFroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public void delete(Integer id) {
        travelgroupDao.deletegroupitem(id);
        travelgroupDao.delete(id);
    }

    @Override
    public TravelGroup getById(Integer id) {
        TravelGroup travelGroup = travelgroupDao.getById(id);
        return travelGroup;
    }

    @Override
    public List<Integer> getitemidsBygroupid(Integer id) {
        List<Integer> list = travelgroupDao.getitemidsBygroupid(id);
        return list;
    }

    @Override
    public void edit(Integer[] id,TravelGroup travelGroup) {
        travelgroupDao.edit(travelGroup);
        deletegroupitemAll();
        Integer groupId = travelGroup.getId();
        setTravelFroupAndTravelItem(groupId,id);
    }

    @Override
    public List<TravelGroup> findAllgroup() {
        return travelgroupDao.findAllgroup();
    }

    private void deletegroupitemAll() {
        travelgroupDao.deletegroupitemAll();
    }

    private void setTravelFroupAndTravelItem(Integer id, Integer[] travelItemIds) {
        Map<String,Integer> map = new HashMap<>();
        for (Integer travelItemId : travelItemIds) {
            map.put("travelGroupId",id);
            map.put("travelItemId",travelItemId);
            travelgroupDao.getTravelFroupAndTravelItem(map);
        }
    }
}