package com.netty.server.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * netty 服务配置类
 * @author suphowe
 */
@Data
@ToString
@Component
@PropertySource(value = "classpath:properties/netty-server-config.properties")
public class NettyServerConfig {

    @Value("${netty.port}")
    int port;

    @Value("${netty.server.threads.max}")
    int maxThread;

    @Value("${netty.pack.length.max}")
    int packMaxLength;
}
