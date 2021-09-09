package com.soft.method.tcp.chatroom;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 客户端线程
 * @author suphowe
 */
public class ClientThread extends Thread{


    BufferedReader br = null;

    public ClientThread(BufferedReader brServer) {
        this.br = brServer;
    }

    @Override
    public void run() {
        try {
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
