package com.soft.method.tcp;

import com.soft.method.tcp.util.TcpClientUtil;

/**
 * TCP协议客户端
 * @author suphowe
 */
public class TcpClient {

    public static void main (String[] args) throws Exception{
        TcpClient tcpClient = new TcpClient();
        tcpClient.client2();
    }

    public void client1(){
        TcpClientUtil tcpClientUtil = new TcpClientUtil();
        tcpClientUtil.sendInfo("127.0.0.1", 5200, TcpClient.getSendInfo());
    }

    public void client2(){
        TcpClientUtil tcpClientUtil = new TcpClientUtil();
        tcpClientUtil.sendSystemIn("127.0.0.1", 5200);
    }





    public static String getSendInfo(){
        String sendInfo = "[name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123]\r\n" +
                "[name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123]\n" +
                "[name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123]\n" +
                "[name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123][name:jim, pwd:123]";
        return sendInfo;
    }
}
