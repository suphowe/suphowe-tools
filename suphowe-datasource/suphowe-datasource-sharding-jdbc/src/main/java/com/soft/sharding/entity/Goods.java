package com.soft.sharding.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 实例
 * @author suphowe
 */
@Entity
@Table(name="goods")
@Data
public class Goods {

    @Id
    private Long goodsId;

    private String goodsName;

    private Long goodsType;
}
