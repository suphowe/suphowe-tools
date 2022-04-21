package com.soft.pgsql.dao;

import com.soft.pgsql.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户表
 * @author suphowe
 */
@Mapper
public interface SysUserMapper {

    /**
     * 查询所有用户
     * @return 所有用户
     */
    List<SysUser> queryAllUser();
}
