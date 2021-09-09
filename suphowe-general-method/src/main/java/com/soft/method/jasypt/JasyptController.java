package com.soft.method.jasypt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据加解密测试
 * @author suphowe
 */
@RestController
@RequestMapping("/jasypt")
@Api(value = "数据加解密测试")
public class JasyptController {

    @Autowired
    StringEncryptor stringEncryptor;

    // 密钥为配置文件中 jasypt.encryptor.password=EbfYkitulv73I2p0mXI50JMXoaxZTKJ7

    @RequestMapping(value = "/encryptInfo", method = RequestMethod.POST)
    @ApiOperation(value = "加密数据")
    public String encryptInfo(String info) {
        return stringEncryptor.encrypt(info);
    }

    @RequestMapping(value = "/decryptInfo", method = RequestMethod.POST)
    @ApiOperation(value = "解密数据")
    public String decryptInfo(String info) {
        return stringEncryptor.decrypt(info);
    }
}
