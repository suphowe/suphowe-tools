package com.soft.web.controller;

import com.soft.web.annotate.ResponseEncrypt;
import com.soft.web.annotate.SysLog;
import com.soft.web.entity.ResponseMsg;
import com.soft.web.service.AesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求测试
 * @author suphowe
 */
@Api(tags = "Aes加解密", description = "加密数据接口")
@RestController
@RequestMapping("/aes")
public class AesController {

    @Autowired
    AesService aesService;

    @ApiOperation(value = "获取加密数据")
    @PostMapping("/getEncrypt")
    @SysLog(module = "加密数据接口", funcType = "request", funcDesc = "获取加密数据")
    @ResponseEncrypt
    public ResponseMsg getEncrypt(String id, String username) {
        return aesService.getEncrypt(id, username);
    }

    @ApiOperation(value = "获取解密数据")
    @PostMapping("/getDecrypt")
    @SysLog(module = "加密数据接口", funcType = "request", funcDesc = "获取解密数据")
    public ResponseMsg getDecrypt(String message){
        return aesService.getDecrypt(message);
    }
}
