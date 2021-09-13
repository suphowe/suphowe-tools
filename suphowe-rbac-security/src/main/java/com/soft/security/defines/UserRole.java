package com.soft.security.defines;

import lombok.Data;

/**
 * 用户角色关联
 * @author suphowe
 */
@Data
public class UserRole {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}
