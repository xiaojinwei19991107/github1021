package com.atguigu.entity;

import java.io.Serializable;

/**
 * Result
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-03-24
 * @Description:
 *             封装的结果集对象
 */
public class Result implements Serializable {
    // 执行的结果 如果交互成功，返回true，交互失败，返回false
    private boolean flag;
    // 返回结果的提示信息
    private String message;
    // 表示前后端进行交互的时候的返回数据
    private Object data;

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}