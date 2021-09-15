package com.soft.method.controller;

import com.soft.method.service.AopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Aop测试
 * @author suphowe
 **/
@RestController
@RequestMapping("/aop")
@Api(value = "Aop切面编程测试")
public class AopController {

    @Autowired
    private AopService aopService;

    @RequestMapping(value = "/methodOne", method = RequestMethod.POST)
    @ApiOperation(value = "方法1")
    public String methodOne(String msg){
        return aopService.methodOne(msg);
    }
}
