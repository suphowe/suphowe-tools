package com.netty.server.system;

import com.netty.server.constant.Constant;
import com.netty.server.constant.TransCodeConstants;
import com.netty.server.controller.NettyMessageController;
import com.netty.server.utils.MsgUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 通道绑定处理逻辑
 * @author suphowe
 */
@Component
@Sharable
public class WebSocketServerHandler extends BaseWebSocketServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

    @Autowired
    NettyMessageController nettyMessageController;

    private WebSocketServerHandshaker webSocketServerHandshaker;

    /**
     * 当客户端主动链接服务端的链接后，调用此方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("与客户端[{}]建立连接", ctx.channel().remoteAddress());
        pushSingle(ctx, "连接成功");
    }

    /**
     * 客户端断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        for(String key: Constant.PUSH_CTX_MAP.keySet()){
            //从连接池内剔除
            if(ctx.equals(Constant.PUSH_CTX_MAP.get(key))){
                logger.info("--->连接池大小:{}", Constant.PUSH_CTX_MAP.size());
                logger.info("--->remove user:{}", key);
                Constant.PUSH_CTX_MAP.remove(key);
                logger.info("--->连接池大小:{}", Constant.PUSH_CTX_MAP.size());
            }

        }
        logger.info("客户端[{}]已断开连接", ctx.channel().remoteAddress());
    }

    /**
     * 读完之后调用的方法
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    /**
     * 读取通道信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object receiveMessage) throws Exception{
        if (receiveMessage == null){
            return;
        }
        if (receiveMessage instanceof FullHttpRequest) {
            //http消息
            handleHttpRequest(ctx, (FullHttpRequest) receiveMessage);
        } else if (receiveMessage instanceof WebSocketFrame) {
            //websocket消息
            handlerWebSocketFrame(ctx, (WebSocketFrame) receiveMessage);
        }
    }

    /**
     * websocket消息处理
     */
    public void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        //关闭请求
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        //ping请求
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //只支持文本格式，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new Exception("仅支持文本格式");
        }
        //客服端发送过来的消息
        String receiveMessage = ((TextWebSocketFrame) frame).text();
        logger.info("接收到客户端[{}]消息:{}", ctx.channel().remoteAddress(), receiveMessage);

        HashMap<String, Object> messageMap = MsgUtil.getReceivedMessage(receiveMessage);
        if (messageMap != null) {
            HashMap<String, Object> verifyAuthority = verifyAuthority(ctx, messageMap);
            int verifyCode = (Integer) verifyAuthority.get("code");
            String verifyMsg = String.valueOf(verifyAuthority.get("message"));
            if (verifyCode != 200) {
                sendBackMessage(messageMap, verifyMsg);
            } else {
                String result = nettyMessageController.businessCategories(messageMap);
                sendBackMessage(messageMap, result);
            }
        } else {
            logger.info("未收到消息或消息缺失");
            pushSingle(ctx, "未收到消息或消息缺失!");
        }
    }

    /**
     * http消息处理
     * 第一次请求是http请求，请求头包括ws的信息
     */
    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel() + "/websocket", null, false);
        webSocketServerHandshaker = wsFactory.newHandshaker(req);
        if (webSocketServerHandshaker == null) {
            //不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            webSocketServerHandshaker.handshake(ctx.channel(), req);
        }
    }

    /**
     * 返回应答给客户端
     */
    public static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 判断是否保持连接
     */
    private static boolean isKeepAlive(FullHttpRequest req) {
        return false;
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        logger.error("服务端捕捉异常: {}", cause.getMessage());
        ctx.close();
    }

    /**
     * 验证用户权限
     */
    private HashMap<String, Object> verifyAuthority(ChannelHandlerContext ctx, HashMap<String, Object> messageMap){
        HashMap<String, Object> result = new HashMap<>(4);
        String user = String.valueOf(messageMap.get("user"));
        String targetUser = String.valueOf(messageMap.get("targetUser"));
        int transcode = Integer.parseInt(String.valueOf(messageMap.get("transcode")));
        if (transcode == 999999){
            if (Constant.PUSH_CTX_MAP.get(user) != null){
                result.put("verify", true);
                result.put("code", 100);
                result.put("message", TransCodeConstants.VERIFY_AUTHORITY.get(100));
                logger.info("用户{}已连接", user);
                return result;
            }
            // 客户端建立连接
            Constant.PUSH_CTX_MAP.put(user, ctx);
            Constant.CHANNEL_GROUP.add(ctx.channel());

            result.put("verify", true);
            result.put("code", 100);
            result.put("message", TransCodeConstants.VERIFY_AUTHORITY.get(100));
            logger.info("添加到连接池：{}", user);
        } else {
            if (Constant.PUSH_CTX_MAP.get(user) == null){
                logger.info("用户{}未登录", user);
                result.put("verify", false);
                result.put("code", 530);
                result.put("message", TransCodeConstants.VERIFY_AUTHORITY.get(530));
            } else if (Constant.PUSH_CTX_MAP.get(targetUser) == null){
                logger.info("目标用户{}未找到", targetUser);
                result.put("verify", false);
                result.put("code", 414);
                result.put("message", TransCodeConstants.VERIFY_AUTHORITY.get(414));
            } else {
                logger.info("验证通过");
                result.put("verify", true);
                result.put("code", 200);
                result.put("message", TransCodeConstants.VERIFY_AUTHORITY.get(200));
            }
        }
        return result;
    }

    /**
     * 返回消息处理
     * @param messageMap 接收消息
     * @param result 需要返回的消息
     */
    private void sendBackMessage(HashMap<String, Object> messageMap, String result) {
        // 将客户端的信息直接返回写入ctx
        // 判断是否进行广播
        int transcode = Integer.parseInt(String.valueOf(messageMap.get("transcode")));
        boolean broadcast = TransCodeConstants.TRANSCODE_BROADCAST.contains(transcode);
        if(broadcast) {
            pushGroup(Constant.CHANNEL_GROUP, result);
        } else {
            String targetUser = String.valueOf(messageMap.get("targetUser"));
            ChannelHandlerContext sendCtx = Constant.PUSH_CTX_MAP.get(targetUser);
            if (sendCtx != null) {
                pushSingle(sendCtx, result);
            }
        }
    }

}
