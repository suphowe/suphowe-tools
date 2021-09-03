package com.netty.server.constant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 * @author suphowe
 */
public class Constant {

    //存放所有的ChannelHandlerContext
    public static Map<String, ChannelHandlerContext> PUSH_CTX_MAP = new ConcurrentHashMap<>() ;

    //存放某一类的channel
    public static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
