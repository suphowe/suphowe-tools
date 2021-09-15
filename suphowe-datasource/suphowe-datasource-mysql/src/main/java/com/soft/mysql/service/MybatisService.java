package com.soft.mysql.service;

import com.soft.mysql.dao.cafmvs.TsUserMapper;
import com.soft.mysql.dao.local.UserMapper;
import com.soft.mysql.entity.TsUser;
import com.soft.mysql.entity.User;
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