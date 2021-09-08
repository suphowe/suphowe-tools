package com.soft.datasource.service;

import com.soft.datasource.dao.cafmvs.TsUserMapper;
import com.soft.datasource.dao.local.UserMapper;
import com.soft.datasource.entity.TsUser;
import com.soft.datasource.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mybatis测试
 * @author suphowe
 */
@Service
public class MybatisService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TsUserMapper TsUserMapper;

    public List<User> queryLocalUser() {
        return userMapper.queryAllUser();
    }

    public List<TsUser> queryCafmvsUser() {
        return TsUserMapper.queryAllUser();
    }

}