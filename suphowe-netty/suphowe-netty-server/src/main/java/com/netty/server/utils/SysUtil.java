package com.netty.server.utils;

import java.util.ResourceBundle;
import java.util.UUID;

/**
 * 数据处理
 * @author suphowe
 */
public class SysUtil {

    public static String getUuid() {
        String randomUuid = UUID.randomUUID().toString();
        return randomUuid.substring(0, 8) + randomUuid.substring(9, 13)
                + randomUuid.substring(14, 18) + randomUuid.substring(19, 23)
                + randomUuid.substring(24);
    }

    public static String getUuid16() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13)
                + uuid.substring(14, 18);
    }
    public static String getProperties(String filename,String param){
        ResourceBundle rb=ResourceBundle.getBundle(filename);
        return rb.getString(param);
    }
}
