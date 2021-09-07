package com.soft.rocketmq.consumer.defines;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * mq消费者配置信息
 * @author suphowe
 */
@Component
@Data
@ToString
@PropertySource(value= {"classpath:properties/consumer.properties"})
public class ConsumerProperties {

    @Value("${rocketmq.consumer.isOnOff}")
    private String consumerIsOnOff;

    @Value("${rocketmq.consumer.groupName}")
    private String consumerGroupName;

    /**
     * mq的nameserver地址
     */
    @Value("${rocketmq.consumer.nameservers}")
    private String consumerNameServers;

    /**
     * 消息最大值
     */
    @Value("${rocketmq.consumer.maxMessageSize}")
    private Integer consumerMaxMessageSize;

    /**
     * 消息发送超时时间
     */
    @Value("${rocketmq.consumer.sendMsgTimeOut}")
    private Integer consumerSendMsgTimeOut;

    /**
     * 失败重试次数
     */
    @Value("${rocketmq.consumer.retryTimesWhenSendFailed}")
    private Integer consumerRetryTimesWhenSendFailed;

    /**
     * 主题
     */
    @Value("${rocketmq.consumer.topic}")
    private String consumerTopic;
}
