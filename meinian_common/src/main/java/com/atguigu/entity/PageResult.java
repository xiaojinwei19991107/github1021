package com.atguigu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * PageResult
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-24
 * @Description:
 *         分页结果集
 */
public class PageResult implements Serializable {
    // 总记录数
    private Long total;
    //当前页结果
    private List rows;

    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
}