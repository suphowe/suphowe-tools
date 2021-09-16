package com.soft.shiro.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * 系统工具类
 * @author suphowe
 */
public class SysUtils {

    /**
     * 返回一个uuid
     */
    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8).concat(uuid.substring(9, 13)).concat(uuid.substring(14, 18)).concat(uuid.substring(19, 23)).concat(uuid.substring(24));
    }

    public static String getUuid16() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8).concat(uuid.substring(9, 13)).concat(uuid.substring(14, 18));
    }

    public static String getProperties(String filename,String param){
        ResourceBundle rb=ResourceBundle.getBundle(filename);
        return rb.getString(param).trim();
    }

    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip.isEmpty() || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip.isEmpty()  || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip.isEmpty()  || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 把字符串数组转换成浮点数组
     */
    public static float[] strToFloat(String[] array){
        if(array.length == 0){
            return null;
        }
        float[] floats = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            floats[i] = Float.parseFloat(array[i]);
        }
        return floats;
    }

    /**
     * 把字符串转换成浮点数组
     */
    public static float[] stringToFloat(String data){
        if(data.isEmpty()){
            return null;
        }
        String[] arr = data.split(",");
        float[] floats = new float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            floats[i] = Float.parseFloat(arr[i]);
        }
        return floats;
    }

    /**
     * 将JSON转换成List
     */
    public static List<Object> jsonToList(String text){
        if(text.isEmpty()){
            return null;
        }
        return JSON.parseObject(text, List.class);
    }

    public static String getThisServerIp(){
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ip = "127.0.0.1";
            ex.printStackTrace();
        }
        return ip;
    }
}
