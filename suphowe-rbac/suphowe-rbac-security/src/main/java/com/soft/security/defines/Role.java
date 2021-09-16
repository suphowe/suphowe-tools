package com.soft.security.defines;

import lombok.Data;

/**
 * 角色
 * @author suphowe
 */
@Data
public class Role {

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;
}
