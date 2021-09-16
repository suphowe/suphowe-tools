package com.netty.server.system;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 发消息方式 抽象出来
 * @author suphowe
 **/
public class BaseWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 推送单个
     **/
    public static void pushSingle(ChannelHandlerContext ctx, String message){
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        Channel channel = ctx.channel();
        channel.write(tws);
        channel.flush();
    }

    /**
     * 群发
     **/
    public static void pushGroup(ChannelGroup ctxGroup, String message){
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        ctxGroup.write(tws);
        ctxGroup.flush();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }
}
