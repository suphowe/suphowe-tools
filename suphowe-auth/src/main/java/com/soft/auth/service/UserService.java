package com.soft.auth.service;

import com.soft.auth.beans.User;
import org.springframework.stereotype.Service;

/**
 * 用户实现类
 * @author suphowe
 */
@Service
public class UserService {

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    public User findUserById(String userId) {
        User user = new User();
        user.setId("admin");
        user.setPassword("111111");
        return user;
    }
}
