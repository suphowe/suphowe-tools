package com.soft.token.constant;


import com.soft.token.utils.SysUtils;

import java.io.Serializable;

/**
 * ecc 参数配置信息
 * @author suphowe
 */
public class EccConstant implements Serializable {

    private static final long serialVersionUID = -8713912989708792752L;

    public static final String ALGORITHM = SysUtils.getProperties("properties/ecc","ecc.algorithm");

    public static final String ECC_PUBLIC_KEY = SysUtils.getProperties("properties/ecc","ecc.default.public.key");

    public static final String ECC_PRIVATE_KEY = SysUtils.getProperties("properties/ecc","ecc.default.private.key");

    public static final int ECC_KEY_SIZE = Integer.parseInt(SysUtils.getProperties("properties/ecc", "ecc.key.size"));

    public static final String CHARSET_ = SysUtils.getProperties("properties/ecc", "ecc.charset");

    public static final String ecc_point_x = "2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8";
    public static final String ecc_point_y = "289070fb05d38ff58321f2e800536d538ccdaa3d9";

}
