package com.netty.client.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;

/**
 * 消息处理
 * @author suphowe
 */
public class MsgUtil {

    /**
     * 处理接收消息
     * @param receiveMessage 接收消息
     * @return 处理后的接收消息
     */
    public static HashMap<String, Object> getReceivedMessage(String receiveMessage) {
        HashMap<String, Object> receiveMap = new HashMap<>(4);
        JSONObject jsonObject = JSONUtil.parseObj(receiveMessage);
        if (jsonObject.get("key") == null){
            return null;
        }
        if (jsonObject.get("transcode") == null){
            return null;
        }
        receiveMap.put("user", jsonObject.get("key"));
        receiveMap.put("transcode", Integer.parseInt(jsonObject.get("transcode").toString()));
        receiveMap.put("data", jsonObject.get("data"));
        return receiveMap;
    }
}
