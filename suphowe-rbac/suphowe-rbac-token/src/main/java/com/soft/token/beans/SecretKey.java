package com.soft.token.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * 公钥 私钥 实体类
 * @author suphowe
 */
@Data
public class SecretKey implements Serializable {

    private static final long serialVersionUID = 7432604817420991603L;

    /**
     * 私钥 JAVA版本的私钥 JAVA读取pkcs8格式比较方便 转换为pkcs8格式的
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;
}
