package com.soft.rocketmq.producer.defines;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * mq生产者配置信息
 * @author suphowe
 */
@Component
@Data
@ToString
@PropertySource(value= {"classpath:properties/producer.properties"})
public class ProducerProperties {

    @Value("${rocketmq.producer.isOnOff}")
    private String producerIsOnOff;

    @Value("${rocketmq.producer.groupName}")
    private String producerGroupName;

    /**
     * mq的nameserver地址
     */
    @Value("${rocketmq.producer.nameservers}")
    private String producerNameServers;

    /**
     * 消息最大值
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer producerMaxMessageSize;

    /**
     * 消息发送超时时间
     */
    @Value("${rocketmq.producer.sendMsgTimeOut}")
    private Integer producerSendMsgTimeOut;

    /**
     * 失败重试次数
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer producerRetryTimesWhenSendFailed;

    /**
     * 主题
     */
    @Value("${rocketmq.producer.topic}")
    private String producerTopic;
}
