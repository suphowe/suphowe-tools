package com.soft.token.utils;

import java.security.MessageDigest;

/**
 * SHA256算法，BTC加密
 * @author suphowe
 */
public class Sha256 {

    /**
     * SHA256加密
     *
     * @param msg 消息
     * @return 加密消息
     * @throws Exception 异常
     */
    public String getSha256(String msg) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(msg.getBytes("UTF-8"));
        return byteToHex(messageDigest.digest());
    }

    /**
     * 将byte转换为16进制
     *
     * @param bytes 消息
     * @return 16进制消息
     */
    private String byteToHex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到1位进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}