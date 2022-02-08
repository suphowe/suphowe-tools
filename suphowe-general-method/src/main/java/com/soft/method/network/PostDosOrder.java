package com.soft.method.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 发送dos命令
 * @author suphowe
 */
public class PostDosOrder {

    private static String dosOrder = "ping 127.0.0.1 -t";
//    private static String dosOrder = "ipconfig";

    public static void main(String[] args) throws Exception {
        // 设置代理IP
//        System.setProperty("http.proxyHost", "internetcn.ford.com");
//        System.setProperty("http.proxyPort", "83");
        //查看一下当前系统的编码方式
        //因为是调用系统的ping命令 返回结果是使用系统的编码的
        String charsetName = System.getProperty("sun.jnu.encoding");;

        String line = null;
        try {
            Process process = Runtime.getRuntime().exec(dosOrder);
            BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream(),charsetName));
            while ((line = buf.readLine()) != null){
                System.out.println(line);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
