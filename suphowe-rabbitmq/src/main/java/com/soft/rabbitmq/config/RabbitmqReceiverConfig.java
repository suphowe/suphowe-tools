package com.soft.rabbitmq.config;

import com.soft.rabbitmq.defines.RabbitmqProperties;
import com.soft.rabbitmq.system.ReceiverAck;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置,消费者消息确认
 * @author suphowe
 */
@Configuration
public class RabbitmqReceiverConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private ReceiverAck receiverAck;

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        //定义消息监听所在的容器工厂,设置容器工厂所用的实例
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        // 并发消费者的初始化数量
        simpleMessageListenerContainer.setConcurrentConsumers(rabbitmqProperties.getConcurrentConsumers());
        // 设置消费者最大并发数量
        simpleMessageListenerContainer.setMaxConcurrentConsumers(rabbitmqProperties.getMaxConcurrentConsumers());
        // 设置消息在传输中的格式，这里采用JSON 的格式进行传输
        // simpleMessageListenerContainer.setMessageConverter(new Jackson2JsonMessageConverter());
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        //设置一个队列
        //simpleMessageListenerContainer.setQueueNames("directQueue");
        //如果同时设置多个如下： 前提是队列都是必须已经创建存在的
        simpleMessageListenerContainer.setQueueNames(rabbitmqProperties.getDirectQueue(), rabbitmqProperties.getMainTopicQueue(),
                rabbitmqProperties.getSecondaryTopicQueue(), rabbitmqProperties.getFanoutQueueMain(),
                rabbitmqProperties.getFanoutQueueSecondary(), rabbitmqProperties.getFanoutQueueThird());


        //另一种设置队列的方法,如果使用这种情况,那么要设置多个,就使用addQueues
        simpleMessageListenerContainer.setQueues(new Queue("directQueue",true));
        simpleMessageListenerContainer.addQueues(new Queue(rabbitmqProperties.getMainTopicQueue(),true));
        simpleMessageListenerContainer.addQueues(new Queue(rabbitmqProperties.getSecondaryTopicQueue(),true));
        simpleMessageListenerContainer.addQueues(new Queue(rabbitmqProperties.getFanoutQueueMain(),true));
        simpleMessageListenerContainer.addQueues(new Queue(rabbitmqProperties.getFanoutQueueSecondary(),true));
        simpleMessageListenerContainer.addQueues(new Queue(rabbitmqProperties.getFanoutQueueThird(),true));

        simpleMessageListenerContainer.setMessageListener(receiverAck);

        return simpleMessageListenerContainer;
    }

}
