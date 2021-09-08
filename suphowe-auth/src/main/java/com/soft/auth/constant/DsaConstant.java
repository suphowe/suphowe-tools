package com.soft.auth.constant;

import com.soft.auth.utils.SysUtils;

import java.io.Serializable;

/**
 * dsa 配置参数信息
 *
 * @author suphowe
 */
public class DsaConstant implements Serializable {

    private static final long serialVersionUID = -5185635334993199662L;

    public static final int DSA_KEY_SIZE = Integer.parseInt(SysUtils.getProperties("properties/dsa", "dsa.key.size"));

    public static final String CHARSET_ = SysUtils.getProperties("properties/dsa", "dsa.charset");

    public static final String ALGORITHM = SysUtils.getProperties("properties/dsa","dsa.algorithm");

    public static final String DEFAULT_SEED = SysUtils.getProperties("properties/dsa","dsa.default.seed");

    public static final String DEFAULT_PUBLIC_KEY = SysUtils.getProperties("properties/dsa","dsa.default.public.key");

    public static final String DEFAULT_PRIVATE_KEY = SysUtils.getProperties("properties/dsa","dsa.default.private.key");


}
