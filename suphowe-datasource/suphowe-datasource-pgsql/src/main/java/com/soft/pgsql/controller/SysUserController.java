package com.soft.pgsql.controller;

import com.google.gson.Gson;
import com.soft.pgsql.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户表
 * @author suphowe
 */
@RestController
@Api(value = "用户表")
@RequestMapping(value = "/user")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/getAllUser")
    @ApiOperation(value = "获取所有用户")
    public String getAllUser() {
        return new Gson().toJson(sysUserService.getAllUser());
    }
}
