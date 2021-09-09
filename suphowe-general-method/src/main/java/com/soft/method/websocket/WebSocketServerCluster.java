package com.soft.method.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.soft.method.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebSocket集群服务
 * @author suphowe
 */
@ServerEndpoint("/WebSocketCluster/{userId}")
@Component
public class WebSocketServerCluster {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerCluster.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, WebSocketServerCluster> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            //加入set中
        } else {
            webSocketMap.put(userId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        logger.info("用户连接:{},当前在线人数为:{}", userId, getOnlineCount());

        try {
            sendMessage(new Gson().toJson(ResultUtil.ok("success")));
        } catch (Exception e) {
            logger.error("用户{} 登陆异常", userId, e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        logger.info("用户退出:{},当前在线人数为:{}", userId, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("用户消息:{},,报文:{}", userId, message);

        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(new Gson().toJson(ResultUtil.ok("success")));
                } else {
                    logger.error("请求的userId:{}不在该服务器上", toUserId);
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            } catch (Exception e) {
                logger.info("发送消息异常", e);
            }
        }
    }

    /**
     * 错误
     * @param session session
     * @param error 错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误:{},原因:{}", this.userId, error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        logger.info("发送消息到:{},报文:{}", userId, message);
        if (StringUtils.isNotBlank(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendMessage(message);
        } else {
            logger.error("用户{},不在线！", userId);
        }
    }

    /**
     * 获取上线数量
     * @return 数量
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 上线数量增加
     */
    public static synchronized void addOnlineCount() {
        WebSocketServerCluster.onlineCount++;
    }

    /**
     * 下线数量减少
     */
    public static synchronized void subOnlineCount() {
        WebSocketServerCluster.onlineCount--;
    }
}
