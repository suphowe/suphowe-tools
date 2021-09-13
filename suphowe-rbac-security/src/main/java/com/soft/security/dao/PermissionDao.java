package com.soft.security.dao;

import com.soft.security.defines.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 权限 DAO
 * @author suphowe
 */
@Mapper
public interface PermissionDao{

    /**
     * 根据角色列表查询权限列表
     *
     * @param ids 角色id列表
     * @return 权限列表
     */
    @Select("<script>" +
            "SELECT DISTINCT sec_permission.* FROM sec_permission,sec_role,sec_role_permission " +
            "WHERE sec_role.id = sec_role_permission.role_id AND sec_permission.id = sec_role_permission.permission_id " +
            "<where>" +
            "   <if test='ids!=null and ids!=&quot;&quot;'>" +
            "       AND sec_role.id IN (#{ids})" +
            "   </if>" +
            "</where>" +
            "</script>"
    )
    List<Permission> selectByRoleIdList(@Param("ids") List<Long> ids);
}
