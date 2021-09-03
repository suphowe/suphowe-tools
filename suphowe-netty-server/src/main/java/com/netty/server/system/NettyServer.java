package com.netty.server.system;

import com.netty.server.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * netty 服务器
 *
 * @author suphowe
 */
@Component
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    NettyServerConfig nettyServerConfig;

    //BS的I/O处理类
    @Autowired
    ServerChannelInitializer serverChannelInitializer;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ChannelFuture channelFuture;

    public NettyServer(){
    }

    public void start() {
        //用于客户端连接请求
        bossGroup = new NioEventLoopGroup(1);
        //用于处理客户端I/O操作
        workerGroup = new NioEventLoopGroup();
        try {
            /*
            （1）boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
            （2）配置客户端的channel类型
            (3)配置TCP参数，握手字符串长度设置
            (4)TCP_NODELAY是一种算法，为了充分利用带宽，尽可能发送大块数据，减少充斥的小块数据，true是关闭，可以保持高实时性,若开启，减少交互次数，但是时效性相对无法保证
            (5)开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
            (6)netty提供了2种接受缓存区分配器，FixedRecvByteBufAllocator是固定长度，但是拓展，AdaptiveRecvByteBufAllocator动态长度
            (7)绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
             */
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //.option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                    .childHandler(serverChannelInitializer);
            // 绑定端口，开始接收进来的连接
            channelFuture = bootstrap.bind(nettyServerConfig.getPort()).sync();
            logger.info("Server start listen at {}", nettyServerConfig.getPort());
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("Exception:", e);
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    //执行之后关闭
    @PreDestroy
    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
