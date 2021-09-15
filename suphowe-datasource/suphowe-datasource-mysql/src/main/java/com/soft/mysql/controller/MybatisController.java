package com.soft.mysql.controller;

import com.google.gson.Gson;
import com.soft.mysql.entity.TsUser;
import com.soft.mysql.entity.User;
import com.soft.mysql.service.MybatisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * mybatis 测试
 * @author suphowe
 */
@RestController
@Api(value = "Mybatis测试")
@RequestMapping(value = "/mybatis")
public class MybatisController {

    @Autowired
    private MybatisService mybatisService;

    @GetMapping("/queryLocalUser")
    @ApiOperation(value = "Mybatis查询测试,查询local")
    public String queryLocalUser() {
        List<User> list = mybatisService.queryLocalUser();
        return new Gson().toJson(list);
    }

    @GetMapping("/queryCafmvsUser")
    @ApiOperation(value = "Mybatis查询测试,查询cafmvs")
    public String queryCafmvsUser() {
        List<TsUser> list = mybatisService.queryCafmvsUser();
        return new Gson().toJson(list);
    }
}
