package com.soft.method.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * socket客户端
 * @author suphowe
 */
public class SocketClient {

    public static void main (String[] args) {
        String ip = "127.0.0.1";
        int port = 5209;
        Socket client = null;
        try{
            client = new Socket(ip, port);
            String msg="发送的数据内容！";
            //得到socket读写流,向服务端程序发送数据
            client.getOutputStream().write(msg.getBytes());
            byte[] datas = new byte[2048];
            //从服务端程序接收数据
            client.getInputStream().read(datas);
            System.out.println(new String(datas));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
