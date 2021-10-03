package com.atguigu.entity;

import java.io.Serializable;

/**
 * QueryPageBean
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-24
 * @Description:
 *         封装查询条件
 */
public class QueryPageBean implements Serializable {
    // 当前页码
    private Integer currentPage = 1;
    // 每个页面展示多少条数据
    private Integer pageSize = 10;
    //查询条件
    private String queryString = "";

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}