package com.netty.server.constant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 交易代码定义
 * @author suphowe
 */
public class TransCodeConstants {

    /**
     * CODE MSG定义
     */
    public static LinkedHashMap<Object, Object> TRANSCODE = new LinkedHashMap<>();
    static {
        TRANSCODE.put(1000, "交易大类1");
        TRANSCODE.put(100001, "子类01");
        TRANSCODE.put(100002, "子类02");
        TRANSCODE.put(100003, "子类03");
        TRANSCODE.put(100004, "子类04");
        // websocket登录
        TRANSCODE.put(999999, "初始化连接");
    }

    /**
     * 需要广播的code
     */
    public static List<Integer> TRANSCODE_BROADCAST = new ArrayList<>();
    static {
        TRANSCODE_BROADCAST.add(100004);
    }

    /**
     * 验证权限
     */
    public static LinkedHashMap<Object, Object> VERIFY_AUTHORITY = new LinkedHashMap<>();
    static {
        VERIFY_AUTHORITY.put(200, "验证通过");
        VERIFY_AUTHORITY.put(401, "未授权:访问被拒绝");
        VERIFY_AUTHORITY.put(4011, "未授权:登陆失败");
        VERIFY_AUTHORITY.put(403, "禁止访问");
        VERIFY_AUTHORITY.put(414, "未找到目标用户");
        VERIFY_AUTHORITY.put(530, "未登录");

    }
}
