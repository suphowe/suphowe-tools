package com.soft.method.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.web.bind.annotation.*;

/**
 * jasypt 配置文件加密
 * @author suphowe
 */
@RestController
@RequestMapping("/jasypt")
@Api(value = "配置文件加密")
@CrossOrigin
@Slf4j
public class JasyptController {

    private StringEncryptor stringEncryptor;

    @ResponseBody
    @RequestMapping(value = "/getJasypt", method = RequestMethod.GET)
    public String getJasypt(String message){
        return stringEncryptor.encrypt(message);
    }
}
