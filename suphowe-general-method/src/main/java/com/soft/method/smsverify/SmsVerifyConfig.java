package com.soft.method.smsverify;

/**
 * 短信验证配置类
 * @author suphowe
 */
public class SmsVerifyConfig {

    /**
     * url前半部分
     */
    public static final String BASE_URL = "https://openapi.danmi.com/distributor/sendSMS";

    /**
     * 开发者注册后系统自动生成的账号，可在官网登录后查看
     */
    public static final String ACCOUNT_SID = "d849b8933a8a5aafdd9d57726a3316ba";

    /**
     * 开发者注册后系统自动生成的TOKEN，可在官网登录后查看
     */
    public static final String AUTH_TOKEN = "5f1c623e9b6df21b2ef0cb8090937a50";

    /**
     * 响应数据类型, JSON或XML
     */
    public static final String RESP_DATA_TYPE = "JSON";
}
