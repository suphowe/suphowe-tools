package com.soft.security.defines;

import lombok.Data;

/**
 * 角色-权限
 * @author suphowe
 */
@Data
public class RolePermission {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;
}
