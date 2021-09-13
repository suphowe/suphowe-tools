package com.soft.token.constant;

import com.soft.token.utils.SysUtils;

import java.io.Serializable;

/**
 * rsa 配置参数信息
 *
 * @author suphowe
 */
public class RsaConstant implements Serializable {
    private static final long serialVersionUID = 1882740985670470103L;

    public static final int MAX_ENCRYPT_BLOCK = Integer.parseInt(SysUtils.getProperties("properties/rsa", "max.encrypt.block"));

    public static final int MAX_DECRYPT_BLOCK = Integer.parseInt(SysUtils.getProperties("properties/rsa","max.decrypt.block"));

    public static final String CHARSET_ = SysUtils.getProperties("properties/rsa", "rsa.charset");

    public static final String ALGORITHM = SysUtils.getProperties("properties/rsa","rsa.algorithm");
}
