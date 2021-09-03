package com.netty.client.system;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * netty 客户端通道绑定处理逻辑
 * @author suphowe
 */
public class BaseClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(BaseClientHandler.class);

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
            CharsetUtil.UTF_8));

    @Autowired
    NettyClient nettyClient;

    /**
     * 通道是否活动
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("建立连接 client：{}", ctx.channel().remoteAddress());
        // 说明见ChannelHandlerContext
        ctx.fireChannelActive();
    }

    /**
     * 与服务端断开连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("断开连接,断开时间为:{}", simpleDateFormat.format(new Date()));
        // 定时线程 断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            try {
                nettyClient.connectNettyServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 10, TimeUnit.SECONDS);
    }

    /**
     * 读取服务器返回信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object receiveMessage){
        if (receiveMessage == null){
            return;
        }
        // 不打印心跳信息
        if ("Heartbeat".equals(receiveMessage.toString())) {
            // logger.info("读取服务器心跳信息：{}", receiveMessage);
        } else {
            logger.info("服务器返回消息：{}", receiveMessage);
        }
        ReferenceCountUtil.release(receiveMessage);
    }

    /**
     * 读完之后调用的方法
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }
//
//    /**
//     * 用户事件
//     */
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleState state = ((IdleStateEvent) evt).state();
//            if (state == IdleState.WRITER_IDLE) {
//                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
//            }
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        logger.error("客户端捕捉异常: {}", cause.getMessage());
        ctx.close();
    }

}
