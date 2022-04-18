package com.soft.web.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * 系统工具类
 * @author suphowe
 */
public class SysUtils {

    /**
     * 返回一个uuid
     */
    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8).concat(uuid.substring(9, 13)).concat(uuid.substring(14, 18)).concat(uuid.substring(19, 23)).concat(uuid.substring(24));
    }

    public static String getUuid16() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8).concat(uuid.substring(9, 13)).concat(uuid.substring(14, 18));
    }

    /**
     * 获取properties 配置文件信息
     * @param filename 文件名称
     * @param param Key名称
     * @return value
     */
    public static String getProperties(String filename, String param){
        ResourceBundle rb=ResourceBundle.getBundle(filename);
        return rb.getString(param).trim();
    }

    /**
     * 获取请求客户端IP
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()  || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()  || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
