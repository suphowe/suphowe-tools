package com.soft.method.udp.constant;

import com.soft.method.utils.SysUtils;

/**
 * udp 静态常量
 * @author suphowe
 */
public class UdpConstants {

    public static final String UDP_HOST = SysUtils.getProperties("properties/udp", "udp.host");

    public static final int UDP_PORT = Integer.parseInt(SysUtils.getProperties("properties/udp", "udp.port"));
}
