package com.soft.token.controller;

import com.google.gson.Gson;
import com.soft.auth.dh.DhAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * dh 算法测试
 *
 * @author suphowe
 */
@Slf4j
@RestController
@Api(value = "DH 算法测试")
public class DhController {

    @GetMapping("/dhTest")
    @ApiOperation(value = "DH测试")
    public String dhTest(String message) {
        /* Test DH */
        // 甲方公钥
        byte[] publicKey1;
        // 甲方私钥
        byte[] privateKey1;
        // 甲方本地密钥
        byte[] secretKey1;
        // 乙方公钥
        byte[] publicKey2;
        // 乙方私钥
        byte[] privateKey2;
        // 乙方本地密钥
        byte[] secretKey2;

        // 初始化密钥 并生成甲方密钥对
        HashMap<String, Object> keyMap1 = DhAlgorithm.initKey();
        publicKey1 = DhAlgorithm.getPublicKey(keyMap1);
        privateKey1 = DhAlgorithm.getPrivateKey(keyMap1);
        log.info("DH 甲方公钥 : {}", DhAlgorithm.fromBytesToHex(publicKey1));
        log.info("DH 甲方私钥 : {}", DhAlgorithm.fromBytesToHex(privateKey1));

        // 乙方根据甲方公钥产生乙方密钥对
        HashMap<String, Object> keyMap2 = DhAlgorithm.initKey(publicKey1);
        publicKey2 = DhAlgorithm.getPublicKey(keyMap2);
        privateKey2 = DhAlgorithm.getPrivateKey(keyMap2);
        log.info("DH 乙方公钥 : {}" + DhAlgorithm.fromBytesToHex(publicKey2));
        log.info("DH 乙方私钥 : {}" + DhAlgorithm.fromBytesToHex(privateKey2));

        // 对于甲方， 根据其私钥和乙方发过来的公钥， 生成其本地密钥secretKey1
        secretKey1 = DhAlgorithm.getSecretKeyBytes(publicKey2, privateKey1);
        log.info("DH 甲方 本地密钥 : {}", DhAlgorithm.fromBytesToHex(secretKey1));

        // 对于乙方， 根据其私钥和甲方发过来的公钥， 生成其本地密钥secretKey2
        secretKey2 = DhAlgorithm.getSecretKeyBytes(publicKey1, privateKey2);
        log.info("DH 乙方 本地密钥 : {}", DhAlgorithm.fromBytesToHex(secretKey2));
        // ---------------------------
        // 测试数据加密和解密
        log.info("加密前的数据:{}", message);
        // 甲方进行数据的加密
        // 用的是甲方的私钥和乙方的公钥
        byte[] encryptDh = DhAlgorithm.encrypt(message.getBytes(), publicKey2, privateKey1);
        log.info("加密后的数据 字节数组转16进制显示:{}", DhAlgorithm.fromBytesToHex(encryptDh));
        // 乙方进行数据的解密
        // 用的是乙方的私钥和甲方的公钥
        byte[] decryptDh = DhAlgorithm.decrypt(encryptDh, publicKey1, privateKey2);
        log.info("解密后数据:{}", new String(decryptDh));

        return new Gson().toJson("success!");
    }
}
