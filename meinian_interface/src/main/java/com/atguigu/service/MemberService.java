package com.atguigu.service;

import com.atguigu.pojo.Member;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * MemberService
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-31
 * @Description:
 */
public interface MemberService{

    Member findBytelephone(String telephone);

    void add(Member member);


    List<Integer> findCountByRegTime(List<String> list);

    int findNewMemberByToday(Date reportDate);

    int findAll();

    int findthisWeekNewMember(Date thisWeekMonday);

    int findthisMonthNewMember(Date firstDay4ThisMonth);
}

