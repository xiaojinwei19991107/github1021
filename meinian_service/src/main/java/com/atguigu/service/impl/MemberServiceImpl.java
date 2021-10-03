package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findBytelephone(String telephone) {
        Member member = memberDao.findmemberBytelephone(telephone);
        return member;
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> findCountByRegTime(List<String> list) {
        List<Integer> memberlist = new ArrayList<>();
        for (String regTime : list) {
            int count = memberDao.findCountByRegTime(regTime);
            memberlist.add(count);
        }
        return memberlist;
    }

    @Override
    public int findNewMemberByToday(Date reportDate) {
        int count = memberDao.findNewMemberByToday(reportDate);
        return count;
    }

    @Override
    public int findAll() {
        return memberDao.findAll();
    }

    @Override
    public int findthisWeekNewMember(Date thisWeekMonday) {
        return memberDao.findthisWeekNewMember(thisWeekMonday);
    }

    @Override
    public int findthisMonthNewMember(Date firstDay4ThisMonth) {
        return memberDao.findthisMonthNewMember(firstDay4ThisMonth);
    }


}
