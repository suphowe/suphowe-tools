package com.soft.pgsql.service;

import com.soft.pgsql.dao.SysUserMapper;
import com.soft.pgsql.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户操作
 * @author suphowe
 */
@Service
public class SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    public List<SysUser> getAllUser(){
        return sysUserMapper.queryAllUser();
    }
}
