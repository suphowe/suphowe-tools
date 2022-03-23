package com.soft.token.symmetric.aes;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * aes 算法 加解密(对称加密) 高级加密标准（AES）
 * @author suphowe
 */
public class AesUtil {

    /**
     * 随机生成密钥对
     * @return 密钥对
     */
    public static byte[] getAesKey() {
        return SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
    }

    /**
     * 构建
     * @param key 密钥对
     * @return 构建
     */
    public static SymmetricCrypto create(byte[] key) {
        return new SymmetricCrypto(SymmetricAlgorithm.AES, key);
    }

    /**
     * 加密字符串为字节数组
     * @param symmetricCrypto 构建AES
     * @param message 字符串
     * @return 加密结果
     */
    public static byte[] encryptByte(SymmetricCrypto symmetricCrypto, String message) {
        return symmetricCrypto.encrypt(message);
    }

    /**
     * 解密加密后的字节数组
     * @param symmetricCrypto 构建AES
     * @param encryptByte 字节数组
     * @return 加密结果
     */
    public static String decryptByte(SymmetricCrypto symmetricCrypto, byte[] encryptByte) {
        return new String(symmetricCrypto.decrypt(encryptByte));
    }

    /**
     * 加密为16进制表示
     * @param symmetricCrypto 构建AES
     * @param message 字符串
     * @return 16进制结果
     */
    public static String encryptHex(SymmetricCrypto symmetricCrypto, String message) {
        return symmetricCrypto.encryptHex(message);
    }

    /**
     * 解密16进制字符串
     * @param symmetricCrypto 构建AES
     * @param encryptHex 16进制字符串
     * @return 解密结果
     */
    public static String decryptHex(SymmetricCrypto symmetricCrypto, String encryptHex) {
        return symmetricCrypto.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }

    /* IOS等移动端对AES加密有要求, 必须为 PKCS7Padding 模式*/
    /* 指定加密模式和偏移 */

    /**
     * 构建AES
     * @return 构建结果
     */
    public static AES createAes() {
        return new AES("CBC",
                "PKCS7Padding",
                // 密钥，可以自定义
                "0123456789ABHAEQ".getBytes(),
                // iv加盐，按照实际需求添加
                "DYgjCEIMVrj2W9xN".getBytes());
    }

    /**
     * 加密为16进制表示
     * @param aes 构建AES
     * @param message 字符串
     * @return 16进制结果
     */
    public static String aesEncryptHex(AES aes, String message) {
        return aes.encryptHex(message);
    }

    /**
     * 解密16进制字符串
     * @param aes 构建AES
     * @param encryptHex 16进制字符串
     * @return 解密结果
     */
    public static String aesDecryptHex(AES aes, String encryptHex) {
        return aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }
}
