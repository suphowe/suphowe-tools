package com.soft.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 *  Book 实体类
 * @author suphowe
 */
@Data
public class Book {

    @Id
    private String id;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 书名
     */
    private String name;
    /**
     * 简介
     */
    private String info;
    /**
     * 出版社
     */
    private String publish;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
