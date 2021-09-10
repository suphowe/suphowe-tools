package com.soft.rabbitmq.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 直连消费者
 * 监听的队列名称 directQueue
 * @author suphowe
 */
@Component
@RabbitListener(queues = "directQueue")
public class RabbitmqDirectReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqDirectReceiver.class);

    @RabbitHandler
    public void process(Map message) {
        logger.info("RabbitMQ<--- RabbitmqDirectReceiver 消费者收到消息:{}", message.toString());
    }
}
