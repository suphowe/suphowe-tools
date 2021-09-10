package com.soft.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置,生产者推送消息的消息确认
 * @author suphowe
 */
@Configuration
public class RabbitmqProviderConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProviderConfig.class);


    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        /*
        1.消息推送到server，但是在server里找不到交换机
        2.消息推送到server，找到交换机了，但是没找到队列
        3.消息推送到sever，交换机和队列啥都没找到
        4.消息推送成功
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            logger.info("RabbitMQ<--- RabbitmqConfig ConfirmCallback 相关数据：{}", correlationData);
            logger.info("RabbitMQ<--- RabbitmqConfig ConfirmCallback 确认情况：{}", ack);
            logger.info("RabbitMQ<--- RabbitmqConfig ConfirmCallback 原因：{}", cause);
        });

        // 1.消息推送到server，找到交换机了，但是没找到队列
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            logger.info("RabbitMQ<--- RabbitmqConfig ReturnCallback 消息：{}", message);
            logger.info("RabbitMQ<--- RabbitmqConfig ReturnCallback 回应码：{}", replyCode);
            logger.info("RabbitMQ<--- RabbitmqConfig ReturnCallback 回应信息：{}", replyText);
            logger.info("RabbitMQ<--- RabbitmqConfig ReturnCallback 交换机：{}", exchange);
            logger.info("RabbitMQ<--- RabbitmqConfig ReturnCallback 路由键：{}", routingKey);
        });
        return rabbitTemplate;
    }
}
