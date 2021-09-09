package com.soft.method.tcp;

import com.soft.method.tcp.util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * Tcp聊天室服务端
 * @author suphowe
 */
public class TcpChatroomServer {

    /**
     * 保存所有的连接
     */
    private HashSet<User> users;

    /**
     * 线程标识
     */
    private boolean run = true;

    public static void main(String[] args) {

        //创建一个Server类
        TcpChatroomServer server = new TcpChatroomServer();
        try {
            //启动服务器
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TcpChatroomServer() {
        run = true;
        users = new HashSet<>();
    }

    public void start() throws IOException {
        //创建一个服务器端
        ServerSocket server = new ServerSocket(8888);
        while (run) {
            //不断接收一个新的连接，利用新连接创建一个User线程进行处理
            Socket client = server.accept();
            User user = new User(client);
            users.add(user);
            new Thread(user).start();
        }
    }

    public void stop() {
        run = false;
    }

    /**
     * 代表一个连接，负责信息的接收与转发
     */
    private class User implements Runnable {

        /**
         * 记录连接用户的名字
         */
        private String name;

        public String getName() {
            return name;
        }

        /**
         * 负责接收
         */
        private DataInputStream is;
        /**
         * 负责发送
         */
        private DataOutputStream os;
        /**
         * 线程标识
         */
        private boolean isRun = true;

        public User(Socket client) {

            try {
                is = new DataInputStream(client.getInputStream());
                os = new DataOutputStream(client.getOutputStream());
                isRun = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                isRun = false;
                CloseUtil.closeAll(is, os);
            }
            try {
                name = is.readUTF();
                this.sendOther("欢迎".concat(name).concat("进入聊天室"), true);
                this.send("系统：您已经进入了聊天室");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (isRun) {
                this.sendOther(this.revice(), false);
            }
        }

        //接收信息
        public String revice() {
            String msg = null;
            try {
                msg = is.readUTF();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return msg;
        }

        //发送信息
        public void send(String msg) {
            try {
                os.writeUTF(msg);
                os.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * 将信息转发给其他用户,同时实现了私聊功能和系统信息功能
         * 因为是内部类，所以可以访问Server类中的private HashSet<User> users
         * .@XX:代表向XX发送私聊信息
         */
        public void sendOther(String msg, boolean admin) {
            if (msg.startsWith("@") && msg.contains(":")) {
                String toname = msg.substring(1, msg.indexOf(":"));
                String newmsg = msg.substring(msg.indexOf(":") + 1);
                for (User user : users) {
                    if (user.getName().equals(toname)) {
                        user.send(this.name + "悄悄的对你说:" + newmsg);
                    }
                }
            } else {
                for (User client : users) {
                    if (client != this) {
                        if (admin) {
                            client.send("系统：" + ":" + msg);
                        } else {
                            client.send(this.name + ":" + msg);
                        }
                    }
                }
            }
        }
    }
}
