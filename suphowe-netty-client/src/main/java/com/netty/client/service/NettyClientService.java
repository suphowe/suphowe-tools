package com.netty.client.service;

import com.google.gson.Gson;
import com.netty.client.system.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * netty 业务处理
 * @author suphowe
 */
@Service
public class NettyClientService {

    @Autowired
    NettyClient nettyClient;

    public String sendMessage(String user, String targetUser, int transcode, String data) {
        HashMap<String, Object> postMessage = new HashMap<>(8);
        postMessage.put("user", user);
        postMessage.put("targetUser", targetUser);
        postMessage.put("transcode", transcode);
        postMessage.put("data", data);
        try {
            nettyClient.sendMessage(new Gson().toJson(postMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success!";
    }
}
