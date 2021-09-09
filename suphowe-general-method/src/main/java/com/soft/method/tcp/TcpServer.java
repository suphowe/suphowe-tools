package com.soft.method.tcp;

import com.soft.method.tcp.util.TcpServerUtil;

/**
 * TCP协议服务端
 * @author suphowe
 */
public class TcpServer {

    public static void main(String[] args) throws Exception {
        TcpServer tcpServer = new TcpServer();
        tcpServer.server();
    }

    public void server(){
        TcpServerUtil tcpServerUtil = new TcpServerUtil();
        tcpServerUtil.startServer(5200);
    }

}
