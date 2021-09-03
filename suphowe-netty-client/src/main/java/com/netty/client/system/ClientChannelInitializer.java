package com.netty.client.system;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 客户端通道初始化
 * @author suphowe
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline p = channel.pipeline();
//        p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
//        p.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//        p.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        p.addLast(new BaseClientHandler());
    }
}
