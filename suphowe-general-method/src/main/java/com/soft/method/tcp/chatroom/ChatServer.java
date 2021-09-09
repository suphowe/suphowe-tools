package com.soft.method.tcp.chatroom;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 聊天室服务器
 *
 * @author suphowe
 */
public class ChatServer {
    private static final int SERVER_PORT = 33000;
    public static ChatMap<String, PrintStream> clients = new ChatMap();

    public void init() {
        try {
            ServerSocket ss = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket socket = ss.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.init();
    }
}
