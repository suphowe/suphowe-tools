package com.soft.security.dao;

import com.soft.security.defines.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 角色 DAO
 * @author suphowe
 */
@Mapper
public interface RoleDao {

    /**
     * 根据用户id 查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    @Select("<script>" +
            "SELECT sec_role.* FROM sec_role,sec_user,sec_user_role " +
            "WHERE sec_user.id = sec_user_role.user_id AND sec_role.id = sec_user_role.role_id " +
            "<where>" +
            "   <if test='userId!=null and userId!=&quot;&quot;'>" +
            "       AND sec_user.id = :userId" +
            "   </if>" +
            "</where>" +
            "</script>"
    )
    List<Role> selectByUserId(@Param("userId") Long userId);
}
