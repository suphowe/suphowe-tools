package com.soft.dynamic.controller;

import com.soft.dynamic.mapper.UserMapper;
import com.soft.dynamic.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户 Controller
 * @author suphowe
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserMapper userMapper;

    /**
     * 获取用户列表
     */
    @GetMapping("/user")
    public List<User> getUserList() {
        return userMapper.selectAll();
    }

}
