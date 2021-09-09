package com.soft.method.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public class WebSocketSendMsg {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketSendMsg.class);

    /**
     * 发送消息到所有前端
     * @param message 消息
     */
    public static void postMsgToFrontal(String message) throws Exception {
        WebSocketServer.sendMessageAll(message);
        logger.info("[WebSocket]发送数据到前端,数据内容:{}", message);
    }

    /**
     * 发送消息到固定前端
     * @param message 消息标识
     */
    public static void postMsgToFrontal(String sid, String message) throws Exception {
        WebSocketServer.sendMessage(sid, message);
        logger.info("[WebSocket]发送数据到前端{},数据内容:{}", sid, message);
    }

    /**
     * WebSocket消息定义
     */
    public static LinkedHashMap<Object,Object> IDENTIFICATION_MSG=new LinkedHashMap<>();
    static{
        IDENTIFICATION_MSG.put(1, "新事件产生");
        IDENTIFICATION_MSG.put(2, "事件状态改变");
        IDENTIFICATION_MSG.put(3, "设备状态变化");
        IDENTIFICATION_MSG.put(4, "交通参数改变");
    }
}
