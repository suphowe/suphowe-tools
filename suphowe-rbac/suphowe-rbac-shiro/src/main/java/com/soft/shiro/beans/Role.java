package com.soft.shiro.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 角色对应实体类
 * @author suphowe
 */
@Data
@AllArgsConstructor
public class Role {

    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;

}
