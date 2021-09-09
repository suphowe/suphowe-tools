package com.soft.method.tcp;

import com.soft.method.tcp.util.TcpReceviceUtil;
import com.soft.method.tcp.util.TcpSendUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * TCP聊天室 客户端
 *
 * @author suphowe
 */
public class TcpChatroomClient {

    public static void main(String[] args) throws Exception{
        //从控制台获得输入
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入您的名字：");
        String name = console.readLine();
        if ("".equals(name)) {
            return;
        }
        Socket client = null;
        try {
            //建立新连接，注意这里创建好就已经连接上了，要保证服务端已经开启
            client = new Socket("localhost", 8888);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.err.println(name + "连接失败");
        }
        //两条线路，一条负责发，一条负责收
        new Thread(new TcpSendUtil(client, name)).start();
        new Thread(new TcpReceviceUtil(client)).start();
    }
}
