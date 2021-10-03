package com.atguigu.dao;

import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    Page findPage(String QueryString);

    void addsetmealAndgroup(Map<String, Integer> map);

    void add(Setmeal setmeal);

    List getSetmeal();

    Setmeal findById(Integer id);

    Setmeal getsetmealById(Integer id);

    List<Map> getSetmealAndValue();
}
