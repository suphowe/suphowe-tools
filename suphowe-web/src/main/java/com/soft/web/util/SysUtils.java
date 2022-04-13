package com.soft.web.util;

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

    public static String getProperties(String filename, String param){
        ResourceBundle rb=ResourceBundle.getBundle(filename);
        return rb.getString(param).trim();
    }
}
