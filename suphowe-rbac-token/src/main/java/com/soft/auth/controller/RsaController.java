package com.soft.auth.controller;

import com.google.gson.Gson;
import com.soft.auth.rsa.RsaAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.util.HashMap;

/**
 * Rsa 算法测试
 * @author suphowe
 */
@RestController
@Api(value = "Rsa 算法测试")
public class RsaController {

    @GetMapping("/getRsaKeys")
    @ApiOperation(value = "获取密钥对")
    public String getRsaKeys() throws Exception {
        KeyPair keyPair = RsaAlgorithm.getKeyPair();
        String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
        String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
        HashMap<String, Object> result = new HashMap<>(4);
        result.put("privateKey", privateKey);
        result.put("publicKey", publicKey);
        return new Gson().toJson(result);
    }

    @PostMapping("/rsaEncrypt")
    @ApiOperation(value = "RSA加密")
    public String rsaEncrypt(String message, String publicKey) throws Exception {
        return RsaAlgorithm.encrypt(message, RsaAlgorithm.getPublicKey(publicKey));
    }

    @PostMapping("/rsaDecrypt")
    @ApiOperation(value = "RSA解密")
    public String rsaDecrypt(String encryptData, String privateKey) throws Exception {
        return RsaAlgorithm.decrypt(encryptData, RsaAlgorithm.getPrivateKey(privateKey));
    }

    @PostMapping("/rsaSign")
    @ApiOperation(value = "RSA签名")
    public String rsaSign(String message, String privateKey) throws Exception {
        return RsaAlgorithm.sign(message, RsaAlgorithm.getPrivateKey(privateKey));
    }

    @PostMapping("/rsaVerify")
    @ApiOperation(value = "RSA验签")
    public boolean rsaVerify(String message, String publicKey, String sign) throws Exception {
        return RsaAlgorithm.verify(message, RsaAlgorithm.getPublicKey(publicKey), sign);
    }

}
