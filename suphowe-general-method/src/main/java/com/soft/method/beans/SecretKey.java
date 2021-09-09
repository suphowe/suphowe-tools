package com.soft.method.beans;

import java.io.Serializable;

/**
 * 公钥 私钥 实体类
 * @author suphowe
 */
public class SecretKey implements Serializable {

    private static final long serialVersionUID = 4213176343689890958L;

    /**
     * 私钥 JAVA版本的私钥 JAVA读取pkcs8格式比较方便 转换为pkcs8格式的
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
