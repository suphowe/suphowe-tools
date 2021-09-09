package com.soft.method.tcp.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * TCP公共类 接收信息
 * @author suphowe
 */
public class TcpReceviceUtil implements Runnable {

    /**
     * 负责读取服务端发送过来的信息
     */
    private DataInputStream is;
    //线程标识
    private boolean isRun = true;


    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (isRun) {
            recevice();
        }
    }

    public TcpReceviceUtil(Socket client) {
        // TODO Auto-generated constructor stub
        try {
            is = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            CloseUtil.closeAll(is);
            isRun = false;
        }
    }

    public void recevice() {
        String msg = null;
        try {
            msg = is.readUTF();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            CloseUtil.closeAll(is);
            isRun = false;
        }
        System.out.println(msg);
    }
}
