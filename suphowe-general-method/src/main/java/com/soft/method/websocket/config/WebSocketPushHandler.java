package com.soft.method.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建处理器,文本消息处理
 * @author suphowe
 */
public class WebSocketPushHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketPushHandler.class);

    private static final List<WebSocketSession> USERS_LIST = new ArrayList<>();

    /**
     * 用户进入系统监听
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("用户{} 进入系统", session.getAttributes().get("userId"));
        Map<String, Object> map = session.getAttributes();
        for (String key : map.keySet()) {
            logger.info("key:{}, value:{}", key, map.get(key));
        }
        USERS_LIST.add(session);
    }

    /**
     * 处理用户请求
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("系统处理用户{} 的请求信息{}", session.getAttributes().get("userId"), message);
    }

    /**
     * 用户退出后的处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        USERS_LIST.remove(session);
        logger.info("用户{}退出系统", session.getAttributes().get("userId"));
    }

    /**
     * 自定义函数
     * 给所有的在线用户发送消息
     */
    public void sendMessagesToUsers(TextMessage message) {
        for (WebSocketSession user : USERS_LIST) {
            try {
                // isOpen()在线就发送
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                logger.error("发送消息异常", e);
            }
        }
    }

    /**
     * 自定义函数
     * 发送消息给指定的在线用户
     */
    public void sendMessageToUser(String userId, TextMessage message) {
        for (WebSocketSession user : USERS_LIST) {
            if (user.getAttributes().get("userId").equals(userId)) {
                try {
                    // isOpen()在线就发送
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    logger.error("发送消息异常", e);
                }
            }
        }
    }

}
