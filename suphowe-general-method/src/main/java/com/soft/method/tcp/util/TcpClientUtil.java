package com.soft.method.tcp.util;

import java.io.*;
import java.net.Socket;

/**
 * TCP 客户端公共方法
 *
 * @author suphowe
 */
public class TcpClientUtil {

    /**
     * 发送数据
     *
     * @param ip   Ip
     * @param port 端口号
     * @param info 发送数据
     */
    public void sendInfo(String ip, int port, String info) {
        try {
            //初始化客户端socket连接
            Socket socket = new Socket(ip, port);
            System.out.println("################### TCP Client Connect " + ip + ":" + port + " ###################");
            //client发送消息
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(info);
            printWriter.flush();
            socket.shutdownOutput();

            //client接收消息
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder result = new StringBuilder();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println("Client->" + str);
                result.append(str);
            }
            System.out.println("Get From TCPServer:" + result);

            //关闭资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            printWriter.close();
            outputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 键盘输入信息发送到服务端
     *
     * @param ip   Ip
     * @param port 端口号
     */
    public void sendSystemIn(String ip, int port) {
        try {
            //初始化客户端socket连接
            Socket socket = new Socket(ip, port);
            System.out.println("################### TCP Client Connect " + ip + ":" + port + " ###################");
            //client发送消息
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            //建立控制台键盘输入
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                printWriter.println(bufferedReader.readLine());
                printWriter.flush();
                if ("END".equals(bufferedReader.readLine())) {
                    break;
                }
            }

            //关闭资源
            bufferedReader.close();
            printWriter.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
