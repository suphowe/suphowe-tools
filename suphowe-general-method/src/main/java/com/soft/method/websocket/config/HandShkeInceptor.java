package com.soft.method.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * WebSocket 握手请求的拦截器。可用于检查握手请求和响应以及将属性传递给目标 WebSocketHandler。
 * @author suphowe
 */
public class HandShkeInceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HandShkeInceptor.class);

    /**
     * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 将返回协议修改为和接收一样
        if(request.getHeaders().containsKey("Sec-WebSocket-Protocol")){
            response.getHeaders().set("Sec-WebSocket-Protocol", request.getHeaders().get("Sec-WebSocket-Protocol").get(0));
        }

        if (request instanceof ServletServerHttpRequest) {
            String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId");
            attributes.put("userId", userId);
            logger.info("用户:{} 请求建立连接", userId);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        logger.info("握手成功后，通常用来注册用户信息");
    }
}
