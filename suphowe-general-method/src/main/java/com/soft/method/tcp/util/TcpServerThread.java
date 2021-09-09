package com.soft.method.tcp.util;

import java.io.*;
import java.net.Socket;

/**
 * TCP 启动线程
 *
 * @author suphowe
 */
public class TcpServerThread extends Thread {

    private Socket socket;

    public TcpServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * 接收数据,接收到END后,停止接收
     */
    @Override
    public void run() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        PrintWriter printWriter = null;

        try {
            // server接收消息
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder result = new StringBuilder();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println("Server Get:->" + str);
                if ("END".equals(str)) {
                    break;
                }
                result.append(str);
            }
            System.out.println("Get From TCPClient:" + result);
            socket.shutdownInput();

            // server发送消息
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.write("Get Info Success");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (outputStream != null) {
                    outputStream.close();

                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
