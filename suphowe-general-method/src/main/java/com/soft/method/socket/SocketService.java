package com.soft.method.socket;

/**
 * socket 服务端
 * @author suphowe
 */
public class SocketService {

    /**搭建服务器端*/
    public static void main(String[] args) {
        SocketJob socketJob = new SocketJob();
        Thread thread = new Thread(socketJob);
        thread.start();
    }

}