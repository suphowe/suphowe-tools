package com.soft.method.tcp.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP Server公共方法
 * @author suuphowe
 */
public class TcpServerUtil {

    /**
     * 开启TCP Server
     */
    public void startServer(int port){
        try {
            // 初始化服务端socket连接，并监听端口的socket请求
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("################### TCP Server Monitor Post " + port + " ###################");
            int count = 0;
            // 处理socket请求
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                TcpServerThread serverThread = new TcpServerThread(socket);
                System.out.println("################### Client Address: " + socket.getInetAddress().getHostAddress());
                serverThread.start();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
