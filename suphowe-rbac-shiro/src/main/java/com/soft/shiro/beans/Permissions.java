package com.soft.shiro.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 权限对应实体类
 * @author suphowe
 */
@Data
@AllArgsConstructor
public class Permissions {
    private String id;
    private String permissionsName;
}
