package com.soft.method.tcp.util;

import java.io.Closeable;

/**
 * 公共类 关闭资源
 *
 * @author suphowe
 */
public class CloseUtil {

    public static void closeAll(Closeable... io) {
        for (Closeable closeable : io) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
