package com.netty.server.system;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务器通道初始化
 * @author suphowe
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    WebSocketServerHandler webSocketServerHandler;

    @Override
    protected void initChannel(SocketChannel channel){
        ChannelPipeline p = channel.pipeline();

         /* LineBasedFrameDecoder的工作原理是：依次遍历ByteBuf中的可读字节，
        判断看其是否有”\n” 或 “\r\n”， 如果有就以此位置为结束位置。
        从可读索引到结束位置的区间的字节就组成了一行。 它是以换行符为结束标志的解码器，
        支持携带结束符和不带结束符两种解码方式，同时支持配置单行的最大长度，
        如果读到了最大长度之后仍然没有发现换行符，则抛出异常，同时忽略掉之前读到的异常码流。*/
        // p.addLast(new LineBasedFrameDecoder(nettyServerConstants.getPackMaxLength()));

        /* 设置心跳检测
         * readerIdleTime:读事件空闲时间，0 则禁用事件
         * writerIdleTime:写事件空闲时间，0 则禁用事件
         * allIdleTime:读或写空闲时间，0 则禁用事件
         * unit:时间格式
         */
        /*
        // 监听读操作,读超时时间为5秒，超过5秒关闭channel;
        p.addLast("ping", new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        // decoder 反序列化
        p.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        // encoder 序列化
        p.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        */

        p.addLast("http-codec", new HttpServerCodec());
        p.addLast("aggregator", new HttpObjectAggregator(65536));
        p.addLast("http-chunked", new ChunkedWriteHandler());
        p.addLast("handler", webSocketServerHandler);
    }
}
