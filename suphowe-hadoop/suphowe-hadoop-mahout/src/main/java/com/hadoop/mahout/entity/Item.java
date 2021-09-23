package com.hadoop.mahout.entity;


import lombok.Data;
import lombok.ToString;

/**
 * 商品实体类
 * @author suphowe
 */
@Data
@ToString
public class Item {

    private Long pid;

    private String name;

    private String types;
}
