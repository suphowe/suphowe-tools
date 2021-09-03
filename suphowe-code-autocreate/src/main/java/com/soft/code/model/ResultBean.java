package com.soft.code.model;

/**
 * 返回数据实体
 * @author suphowe
 */
public class ResultBean {

    private Integer status;
    private String msg;
    private Object obj;

    public static ResultBean ok(String msg,Object obj) {
        return new ResultBean(200, msg, obj);
    }


    public static ResultBean ok(String msg) {
        return new ResultBean(200, msg, null);
    }


    public static ResultBean error(String msg,Object obj) {
        return new ResultBean(500, msg, obj);
    }


    public static ResultBean error(String msg) {
        return new ResultBean(500, msg, null);
    }

    private ResultBean() {
    }

    private ResultBean(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
