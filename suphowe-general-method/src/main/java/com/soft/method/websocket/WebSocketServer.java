package com.soft.method.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebSocket服务
 * @author suphowe
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private final static ConcurrentHashMap<String, WebSocketServer> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收sid
     */
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     * @param session 缓存
     * @param sid 用户标识
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        if (WEBSOCKET_MAP.containsKey(sid)) {
            WEBSOCKET_MAP.remove(sid);
            WEBSOCKET_MAP.put(sid, this);
            //加入set中
        } else {
            WEBSOCKET_MAP.put(sid, this);
        }

        try {
            logger.info("用户{}连接成功!", sid);
            sendMessage("连接成功");
        } catch (IOException e) {
            logger.error("用户:{},网络异常!!!!!!", sid);
        }
    }

    /**
     * 接收用户消息
     * @param message 消息内容
     */
    @OnMessage
    public void onMessage(String message) {
        logger.info("接收到前端消息:{}", message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (WEBSOCKET_MAP.containsKey(sid)) {
            WEBSOCKET_MAP.remove(sid);
            logger.info("关闭前端sid:{} 连接", sid);
        }
    }

    /**
     * 连接错误调用方法
     * @param session 缓存信息
     * @param error 错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误:{},原因:{}", sid, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     * @param message 推送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 给所有的Websocket对象发送自定义消息
     * @param message 推送消息
     **/
    public static void sendMessageAll(String message) throws IOException {
        for (String sid : WEBSOCKET_MAP.keySet()) {
            WEBSOCKET_MAP.get(sid).sendMessage(message);
        }
    }

    /**
     * 发送消息到固定前端
     * @param sid     前段标识
     * @param message 消息内容
     */
    public static void sendMessage(String sid, String message) throws Exception {
        WEBSOCKET_MAP.get(sid).sendMessage(message);
    }
}
