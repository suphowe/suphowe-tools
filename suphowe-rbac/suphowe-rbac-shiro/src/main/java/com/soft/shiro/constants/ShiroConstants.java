package com.soft.shiro.constants;

import com.soft.shiro.utils.SysUtils;

/**
 * shiro 静态常量
 * @author suphowe
 */
public class ShiroConstants {

    public static final long EXPIRATION = 1000 * Long.parseLong(SysUtils.getProperties("properties/shiro", "shiro.session.expiration"));
}
