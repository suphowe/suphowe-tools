package com.netty.client.utils;

import java.util.ResourceBundle;
import java.util.UUID;

/**
 * 数据处理
 * @author suphowe
 */
public class SysUtil {

    public static String getuuid() {
        String randomuuid = UUID.randomUUID().toString();
        return randomuuid.substring(0, 8) + randomuuid.substring(9, 13)
                + randomuuid.substring(14, 18) + randomuuid.substring(19, 23)
                + randomuuid.substring(24);
    }

    public static String getuuid16() {
        String struuid = UUID.randomUUID().toString();
        return struuid.substring(0, 8) + struuid.substring(9, 13)
                + struuid.substring(14, 18);
    }
    public static String getProperties(String filename,String param){
        ResourceBundle rb=ResourceBundle.getBundle(filename);
        return rb.getString(param);
    }
}
