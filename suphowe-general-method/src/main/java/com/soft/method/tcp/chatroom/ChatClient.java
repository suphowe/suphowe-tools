package com.soft.method.tcp.chatroom;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 聊天客户端
 * @author suphowe
 */
public class ChatClient {
    private static final int SERVER_PORT = 33000;
    private Socket socket;
    private PrintStream ps;
    private BufferedReader brServer;
    private BufferedReader keyIn;

    public void init() {
        try {
            keyIn = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket("127.0.0.1", SERVER_PORT);
            ps = new PrintStream(socket.getOutputStream());
            brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String tip = "";
            while(true) {
                String userName = JOptionPane.showInputDialog(tip + "输入用户名");
                ps.println(ChatProtocol.USER_ROND + userName + ChatProtocol.USER_ROND);

                //服务器端响应
                String result = brServer.readLine();
                if(result.equals(ChatProtocol.NAME_REP)) {
                    tip = "用户名重复，请重新输入";
                    continue;
                }
                //登录成功
                if(result.equals(ChatProtocol.LOGIN_SUCCESS)) {
                    System.out.println("登录成功，账号： ["+ userName +"]");
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        new ClientThread(brServer).start();
    }

    private void readAndSend() {
        try {
            String line = null;
            while((line = keyIn.readLine()) != null) {
                //如果发送的消息中带有冒号，且以//开头，则认为是私聊信息
                if(line.indexOf(":") > 0 && line.startsWith("//")) {
                    line = line.substring(2);
                    ps.println(ChatProtocol.PRIVATE_ROND
                            + line.split(":")[0]
                            + ChatProtocol.SPLIT_SIGN
                            + line.split(":")[1]
                            + ChatProtocol.PRIVATE_ROND);
                } else {
                    ps.println(ChatProtocol.MSG_ROND + line + ChatProtocol.MSG_ROND);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void closeRs() {
        try {
            if (keyIn != null) {
                keyIn.close();
            }

            if (brServer != null) {
                brServer.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.init();
        client.readAndSend();
    }
}
