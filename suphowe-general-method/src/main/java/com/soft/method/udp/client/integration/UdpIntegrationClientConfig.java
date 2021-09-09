package com.soft.method.udp.client.integration;

import com.soft.method.udp.constant.UdpConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;

/**
 * unicast 发送消息
 * @author suphowe
 */
@Configuration
public class UdpIntegrationClientConfig {

    @Bean
    @ServiceActivator(inputChannel = "udpOut")
    public UnicastSendingMessageHandler unicastSendingMessageHandler() {
        UnicastSendingMessageHandler unicastSendingMessageHandler = new UnicastSendingMessageHandler(UdpConstants.UDP_HOST, UdpConstants.UDP_PORT);
        return unicastSendingMessageHandler;
    }

}
