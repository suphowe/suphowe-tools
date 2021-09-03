package com.netty.client.system;

import com.netty.client.config.NettyClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * netty 客户端
 *
 * @author suphowe
 */
@Component
public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    @Autowired
    NettyClientConfig nettyClientConfig;

    private Bootstrap bootstrap;

    private Channel channel;

    private ChannelFuture channelFuture;


    /**
     * 线程池
     */
    private final ExecutorService executorService = new ThreadPoolExecutor(10, 100, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
    /*ThreadPoolExecutor(corePoolSize:线程池中的线程数量,
     *                      maximumPoolSize:最大线程数量,
     *                      keepAliveTime:空闲线程等待时间,
     *                      unit:keepAliveTime的单位,
     *                      workQueue：任务队列,
     *                      threadFactory：线程工厂,
     *                      handler：拒绝策略);
     */

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(nettyClientConfig.getHost(), nettyClientConfig.getPort())
                    .handler(new ClientChannelInitializer());
            connectNettyServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        executorService.submit(() -> {
            try {
                bootstrap.option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ClientChannelInitializer(NettyClient.this));

                connectNettyServer();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        });
        */
    }

    /**
     * 发送消息
     */
    public void sendMessage(String message) throws Exception {
        channelFuture.channel().writeAndFlush(message);
        channelFuture.channel().closeFuture().sync();
    }

    /**
     * 连接服务端
     */
    public void connectNettyServer() throws Exception {
        if (channel != null && channel.isActive()) {
            return;
        }
        channelFuture = bootstrap.connect(nettyClientConfig.getHost(), nettyClientConfig.getPort()).sync();
        channelFuture.addListener((ChannelFutureListener) futureListener -> {
            if (channelFuture.isSuccess()) {
                channel = futureListener.channel();
                logger.info("connect server successfully");
            } else {
                System.out.println("Failed to connect to server, try connect after 10s");
                futureListener.channel().eventLoop().schedule(() -> {
                    try {
                        connectNettyServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 10, TimeUnit.SECONDS);
            }
        });

    }
}
