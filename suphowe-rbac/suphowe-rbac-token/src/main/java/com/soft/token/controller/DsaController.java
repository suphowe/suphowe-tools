package com.soft.token.controller;

import com.google.gson.Gson;
import com.soft.token.dsa.DsaAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Dsa 算法测试
 * @author suphowe
 */
@RestController
@Api(value = "Dsa 算法测试")
public class DsaController {

    @GetMapping("/getDsaKeys")
    @ApiOperation(value = "获取密钥对")
    public String getRsaKeys() throws Exception {
        return new Gson().toJson(DsaAlgorithm.initKey());
    }

    @GetMapping("/getDsaPrivateKey")
    @ApiOperation(value = "取得私钥")
    public String getDsaPrivateKey() throws Exception {
        HashMap<String, Object> keys = DsaAlgorithm.initKey();
        return DsaAlgorithm.getPrivateKey(keys);
    }


    @GetMapping("/getDsaPublicKey")
    @ApiOperation(value = "取得公钥")
    public String getDsaPublicKey() throws Exception {
        HashMap<String, Object> keys = DsaAlgorithm.initKey();
        return DsaAlgorithm.getPublicKey(keys);
    }

    @PostMapping("/dsaSign")
    @ApiOperation(value = "DSA签名")
    public String dsaSign(String message, String privateKey) throws Exception {
        return DsaAlgorithm.sign(message, privateKey);
    }

    @PostMapping("/dsaVerify")
    @ApiOperation(value = "DSA验签")
    public boolean dsaVerify(String message, String publicKey, String sign) throws Exception {
        return DsaAlgorithm.verify(message, publicKey, sign);
    }
}
