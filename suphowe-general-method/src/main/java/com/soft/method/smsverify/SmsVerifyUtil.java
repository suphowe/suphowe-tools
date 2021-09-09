package com.soft.method.smsverify;

import java.util.Random;

/**
 * 短信发送工具类
 * @author suphowe
 */
public class SmsVerifyUtil {

    /**
     * 生成4位随机数
     * @return 4位随机数
     */
    public static String runNumber() {
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}
