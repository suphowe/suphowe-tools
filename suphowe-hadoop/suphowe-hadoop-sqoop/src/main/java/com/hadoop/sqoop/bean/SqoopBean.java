package com.hadoop.sqoop.bean;


import java.sql.Timestamp;

/**
 * sqoop 返回实体类
 * @author suphowe
 */
public class SqoopBean {

    /**
     * 返回0正确，返回1错误
     */
    private int i;

    /**
     * 时间
     */
    private Timestamp ts;

    public int getI() {
        return i;
    }

    public int setI(int i) {
        this.i = i;
        return i;
    }

    @Override
    public String toString() {
        return "sqoopBean{" +
                "i=" + i +
                ", ts=" + ts +
                '}';
    }

    public Timestamp getTs() {
        return ts;
    }

    public Timestamp setTs(Timestamp ts) {
        this.ts = ts;
        return ts;
    }
}


