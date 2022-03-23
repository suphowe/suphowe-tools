package com.soft.token.symmetric.desede;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * DESede 算法 加解密(对称加密) 数据加密标准（DES)
 * @author suphowe
 */
public class DeSedeUtil {

    /**
     * 随机生成密钥对
     * @return 密钥对
     */
    public static byte[] getDeSedeKey() {
        return SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();
    }

    /**
     * 构建
     * @param key 密钥对
     * @return 构建
     */
    public static SymmetricCrypto create(byte[] key) {
        return new SymmetricCrypto(SymmetricAlgorithm.DESede, key);
    }

    /**
     * 加密字符串为字节数组
     * @param symmetricCrypto 构建DESede
     * @param message 字符串
     * @return 加密结果
     */
    public static byte[] encryptByte(SymmetricCrypto symmetricCrypto, String message) {
        return symmetricCrypto.encrypt(message);
    }

    /**
     * 解密加密后的字节数组
     * @param symmetricCrypto 构建DESede
     * @param encryptByte 字节数组
     * @return 加密结果
     */
    public static String decryptByte(SymmetricCrypto symmetricCrypto, byte[] encryptByte) {
        return new String(symmetricCrypto.decrypt(encryptByte));
    }

    /**
     * 加密为16进制表示
     * @param symmetricCrypto 构建DESede
     * @param message 字符串
     * @return 16进制结果
     */
    public static String encryptHex(SymmetricCrypto symmetricCrypto, String message) {
        return symmetricCrypto.encryptHex(message);
    }

    /**
     * 解密16进制字符串
     * @param symmetricCrypto 构建DESede
     * @param encryptHex 16进制字符串
     * @return 解密结果
     */
    public static String decryptHex(SymmetricCrypto symmetricCrypto, String encryptHex) {
        return symmetricCrypto.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }

    /* DES全称为Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法，Java中默认实现为：DES/CBC/PKCS5Padding */
    /* 指定加密模式和偏移 */

    /**
     * 构建DES
     * @return 构建结果
     */
    public static DES createAes() {
        return new DES(Mode.CTS,
                Padding.PKCS5Padding,
                // 密钥，可以自定义
                "0CoJUm6Qyw8W8jud".getBytes(),
                // iv加盐，按照实际需求添加
                "01020304".getBytes());
    }

    /**
     * 加密为16进制表示
     * @param des 构建DES
     * @param message 字符串
     * @return 16进制结果
     */
    public static String aesEncryptHex(DES des, String message) {
        return des.encryptHex(message);
    }

    /**
     * 解密16进制字符串
     * @param des 构建DES
     * @param encryptHex 16进制字符串
     * @return 解密结果
     */
    public static String aesDecryptHex(DES des, String encryptHex) {
        return des.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }
}
