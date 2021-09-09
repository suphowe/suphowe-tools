package com.soft.method.constants;

import com.soft.method.utils.SysUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

/**
 * 返回码自定义
 * @author suphowe
 */
public class Constants {

    public static String NODE_NAME;

    static {
        try {
            NODE_NAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求头 token 名称
     */
    public static String TOKEN_NAME = "TOKEN";
    public static String TOKEN_PREFIX = "TOKEN_PREFIX";

    /**
     * CODE MSG定义
     */
    public static LinkedHashMap<Object, Object> CODE_MSG_MAP = new LinkedHashMap<>();
    static {
        CODE_MSG_MAP.put(0, "SUCCESS!");
        CODE_MSG_MAP.put(1, "Service Error");
        CODE_MSG_MAP.put(2, "Data Insert Error");
        CODE_MSG_MAP.put(3, "没有可用于计算的预置点 Cannot Find Used Preset");
        CODE_MSG_MAP.put(4, "相机使用状态更新失败 Failed Update Camera Used Status");
        CODE_MSG_MAP.put(5, "未接收到算法数据，接收数据为空 Detection Data Is Null");
        CODE_MSG_MAP.put(6, "Json处理失败 Json Dispose Error");
        CODE_MSG_MAP.put(7, "TOKEN已过期,关闭数据接收 Token Timeout,Close Data Reception");
        CODE_MSG_MAP.put(8, "无区域告警数据 Cannot Find Area Alarm Data");
        CODE_MSG_MAP.put(9, "区域信息已更新,数据无效,请重新获取区域信息 Area Data Updated,Please Afresh Config");
        CODE_MSG_MAP.put(10, "处理异常上报事件失败 Failed Save Valid Detection Data");
        CODE_MSG_MAP.put(11, "VIDEO已回收 Video Update");
        CODE_MSG_MAP.put(12, "VIDEO已过期 Video Timeout,Close Data Reception");
        CODE_MSG_MAP.put(13, "设备IP与服务IP未绑定 Device Ip Not Bind Server Ip");
    }

    /**
     * NC值，通常为：00000001
     */
    public static final String CONST_NC = "00000001";
    /**
     * http get方式
     */
    public static final String CONST_METHOD_GET = "GET";
    /**
     * http post方式
     */
    public static final String CONST_METHOD_POST = "POST";
    /**
     * http code 401
     */
    public static final int UNAUTHORIZED_CODE = 401;
    /**
     * http code 200
     */
    public static final int HTTP_OK = 200;
    /**
     * 使用一个固定的cnonce值
     */
    public static final String CONST_CNONCE = "1806de32efd48d8d";

    /**
     * 静态变量获取测试
     */
    public static final String STATIC_VALUE = SysUtils.getProperties("properties/constants", "static.value");
}
