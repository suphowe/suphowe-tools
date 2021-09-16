package com.soft.token.constant;

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
}
