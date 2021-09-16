package com.netty.client.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * netty 客户端配置类
 * @author suphowe
 */
@Data
@ToString
@Component
@PropertySource(value = "classpath:properties/netty-client-config.properties")
public class NettyClientConfig {

    @Value("${netty.port}")
    int port;

    @Value("${netty.host}")
    String host;

    @Value("${netty.size}")
    int size;

    @Value("${netty.pack.length.max}")
    int packMaxLength;

}
