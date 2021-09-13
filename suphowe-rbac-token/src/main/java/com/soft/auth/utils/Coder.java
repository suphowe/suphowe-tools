package com.soft.auth.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * 基础加密组件
 * @author suphowe
 */
public abstract class Coder {

    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * BASE64解密
     */
    public static byte[] decryptBase64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     */
    public static String encryptBase64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5加密
     */
    public static byte[] encryptMd5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    /**
     * SHA加密
     */
    public static byte[] encryptSha(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }

    /**
     * 初始化HMAC密钥
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBase64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     */
    public static byte[] encryptHmac(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBase64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }
}
