package com.netty.client.thread;

import com.netty.client.system.NettyClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化执行,工程启动时执行
 * @author suphowe
 */
@Component
@Order(1)
public class StartUpRunner implements CommandLineRunner {

    @Resource
    NettyClient nettyClient;

    @Override
    public void run(String... args) throws Exception {
        nettyClient.run();
    }
}
