package com.atguigu.dao;

import com.atguigu.pojo.Member;

import java.util.Date;
import java.util.List;

/**
 * MemberDao
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-31
 * @Description:
 */
public interface MemberDao {

    Member findmemberBytelephone(String telephone);

    void add(Member member);

    int findCountByRegTime(String regTime);

    int findNewMemberByToday(Date reportDate);

    int findAll();

    int findthisWeekNewMember(Date thisWeekMonday);

    int findthisMonthNewMember(Date firstDay4ThisMonth);

}

