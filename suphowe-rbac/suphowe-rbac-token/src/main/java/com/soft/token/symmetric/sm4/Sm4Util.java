package com.soft.token.symmetric.sm4;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * Sm4 算法 加解密(对称加密)
 * @author suphowe
 */
public class Sm4Util {

    /**
     * 构建SM4
     * @return 构建
     */
    public static SymmetricCrypto create() {
        return new SymmetricCrypto("SM4");
    }

    /**
     * 加密为16进制表示
     * @param symmetricCrypto 构建SM4
     * @param message 字符串
     * @return 16进制结果
     */
    public static String encryptHex(SymmetricCrypto symmetricCrypto, String message) {
        return symmetricCrypto.encryptHex(message);
    }

    /**
     * 解密16进制字符串
     * @param symmetricCrypto 构建SM4
     * @param encryptHex 16进制字符串
     * @return 解密结果
     */
    public static String decryptHex(SymmetricCrypto symmetricCrypto, String encryptHex) {
        return symmetricCrypto.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }

    /* 指定加密模式和偏移 */

    /**
     * 构建SM4
     * @return 构建结果
     */
    public static SymmetricCrypto createSm4() {
        return new SymmetricCrypto("SM4/ECB/PKCS5Padding");
    }
}
