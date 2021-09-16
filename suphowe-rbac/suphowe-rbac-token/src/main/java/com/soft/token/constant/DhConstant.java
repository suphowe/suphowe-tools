package com.soft.token.constant;

import com.soft.token.utils.SysUtils;

import java.io.Serializable;

/**
 * dh 配置参数信息
 *
 * @author suphowe
 */
public class DhConstant implements Serializable {

    private static final long serialVersionUID = -1009602490891371355L;

    public static final String DH_PAIR_KEY = SysUtils.getProperties("properties/dh", "dh.default.pair.key");

    public static final String DH_PUBLIC_KEY = SysUtils.getProperties("properties/dh", "dh.default.public.key");

    public static final String DH_PRIVATE_KEY = SysUtils.getProperties("properties/dh", "dh.default.private.key");

    public static final String DH_ALGORITHM = SysUtils.getProperties("properties/dh", "dh.default.algorithm");

    public static final int SIZE = Integer.parseInt(SysUtils.getProperties("properties/dh", "dh.default.size"));
}
