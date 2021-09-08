package com.soft.auth.dh;

import com.soft.auth.constant.DhConstant;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

/**
 * dh 算法
 * @author suphowe
 */
public class DhAlgorithm {

    /**
     * A初始化并返回密钥对
     */
    public static HashMap<String, Object> initKey() {
        try {
            // 实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(DhConstant.DH_PAIR_KEY);
            // 初始化密钥对生成器
            keyPairGenerator.initialize(DhConstant.SIZE);
            // 生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 得到甲方公钥
            DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
            // 得到甲方私钥
            DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
            // 将公钥和私钥封装在Map中， 方便之后使用
            HashMap<String, Object> keyMap = new HashMap<>(4);
            keyMap.put(DhConstant.DH_PUBLIC_KEY, publicKey);
            keyMap.put(DhConstant.DH_PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * B根据A公钥初始化并返回密钥对
     *
     * @param key A的公钥
     */
    public static HashMap<String, Object> initKey(byte[] key) {
        try {
            // 将甲方公钥从字节数组转换为PublicKey
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(DhConstant.DH_PAIR_KEY);
            // 产生甲方公钥pubKey
            DHPublicKey dhPublicKey = (DHPublicKey) keyFactory
                    .generatePublic(keySpec);
            // 剖析甲方公钥，得到其参数
            DHParameterSpec dhParameterSpec = dhPublicKey.getParams();
            // 实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(DhConstant.DH_PAIR_KEY);
            // 用甲方公钥初始化密钥对生成器
            keyPairGenerator.initialize(dhParameterSpec);
            // 产生密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 得到乙方公钥
            DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
            // 得到乙方私钥
            DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
            // 将公钥和私钥封装在Map中， 方便之后使用
            HashMap<String, Object> keyMap = new HashMap<>(4);
            keyMap.put(DhConstant.DH_PUBLIC_KEY, publicKey);
            keyMap.put(DhConstant.DH_PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 根据对方的公钥和自己的私钥生成 本地密钥,返回的是SecretKey对象的字节数组
     *
     * @param publicKey 公钥
     * @param privateKey 私钥
     */
    public static byte[] getSecretKeyBytes(byte[] publicKey, byte[] privateKey) {
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(DhConstant.DH_PAIR_KEY);
            // 将公钥从字节数组转换为PublicKey
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            // 将私钥从字节数组转换为PrivateKey
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(privateKey);
            PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);

            // 准备根据以上公钥和私钥生成本地密钥SecretKey
            // 先实例化KeyAgreement
            KeyAgreement keyAgreement = KeyAgreement.getInstance(DhConstant.DH_PAIR_KEY);
            // 用自己的私钥初始化keyAgreement
            keyAgreement.init(priKey);
            // 结合对方的公钥进行运算
            keyAgreement.doPhase(pubKey, true);
            // 开始生成本地密钥SecretKey 密钥算法为对称密码算法
            SecretKey secretKey = keyAgreement.generateSecret(DhConstant.DH_ALGORITHM);
            return secretKey.getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 根据对方的公钥和自己的私钥生成 本地密钥,返回的是SecretKey对象
     *
     * @param publicKey 公钥
     * @param privateKey 私钥
     */
    public static SecretKey getSecretKey(byte[] publicKey, byte[] privateKey) {
        try {
            // 实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(DhConstant.DH_PAIR_KEY);
            // 将公钥从字节数组转换为PublicKey
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            // 将私钥从字节数组转换为PrivateKey
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(privateKey);
            PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);

            // 准备根据以上公钥和私钥生成本地密钥SecretKey
            // 先实例化KeyAgreement
            KeyAgreement keyAgreement = KeyAgreement.getInstance(DhConstant.DH_PAIR_KEY);
            // 用自己的私钥初始化keyAgreement
            keyAgreement.init(priKey);
            // 结合对方的公钥进行运算
            keyAgreement.doPhase(pubKey, true);
            // 开始生成本地密钥SecretKey 密钥算法为对称密码算法
            return keyAgreement.generateSecret(DhConstant.DH_ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 从 Map 中取得公钥
     */
    public static byte[] getPublicKey(HashMap<String, Object> keyMap) {
        DHPublicKey key = (DHPublicKey) keyMap.get(DhConstant.DH_PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * 从 Map 中取得私钥
     */
    public static byte[] getPrivateKey(HashMap<String, Object> keyMap) {
        DHPrivateKey key = (DHPrivateKey) keyMap.get(DhConstant.DH_PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * DH 加密
     *
     * @param data 带加密数据
     * @param publicKey A公钥
     * @param privateKey B私钥
     */
    public static byte[] encrypt(byte[] data, byte[] publicKey,byte[] privateKey) {
        byte[] bytes = null;
        try {
            //
            SecretKey secretKey = getSecretKey(publicKey, privateKey);
            // 数据加密
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            bytes = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * DH 解密
     *
     * @param data 待解密数据
     * @param publicKey B公钥
     * @param privateKey A私钥
     */
    public static byte[] decrypt(byte[] data, byte[] publicKey,
                                   byte[] privateKey) {
        byte[] bytes = null;
        try {
            //
            SecretKey secretKey = getSecretKey(publicKey, privateKey);
            // 数据加密
            Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            bytes = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 字节数组转16进制
     */
    public static String fromBytesToHex(byte[] resultBytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < resultBytes.length; i++) {
            if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
                builder.append("0").append(
                        Integer.toHexString(0xFF & resultBytes[i]));
            } else {
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }
}
