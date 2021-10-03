package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelgroupDao {
    List<TravelItem> findAll();

    Page findPage(String queryString);


    void add(TravelGroup travelGroup);

    void getTravelFroupAndTravelItem(Map<String, Integer> map);

    void delete(Integer id);

    void deletegroupitem(Integer id);

    TravelGroup getById(Integer id);

    List<Integer> getitemidsBygroupid(Integer id);

    void edit(TravelGroup travelGroup);

    void deletegroupitemAll();

    List<TravelGroup> findAllgroup();

    List<TravelGroup> findTravelGroupById(Integer id);
}
