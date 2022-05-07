package com.soft.websocket.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * websocket服务类 ServerEndpoint("/webSocket/{uid}") 前端通过此URI与后端建立链接
 * @author suphowe
 */

@ServerEndpoint("/webSocket/{uid}")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */
    private static final AtomicInteger ONLINE_NUM = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象
     */
    private static final CopyOnWriteArraySet<Session> SESSION_POOLS = new CopyOnWriteArraySet<>();

    /**
     * 有客户端连接成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid){
        SESSION_POOLS.add(session);
        ONLINE_NUM.incrementAndGet();
        logger.info(uid + "加入webSocket！当前人数为" + ONLINE_NUM);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        SESSION_POOLS.remove(session);
        int cnt = ONLINE_NUM.decrementAndGet();
        logger.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 发送消息
     */
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 群发消息
     */
    public void broadCastInfo(String message) throws IOException {
        for (Session session : SESSION_POOLS) {
            if(session.isOpen()){
                sendMessage(session, message);
            }
        }
    }

    /**
     * 发生错误
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        logger.error("发生错误");
        throwable.printStackTrace();
    }

}
