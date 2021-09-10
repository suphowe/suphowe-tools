package com.soft.rabbitmq.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 主题消费者
 * 监听的队列名称 topic.main
 * @author suphowe
 */
@Component
@RabbitListener(queues = "topic.main")
public class RabbitmqTopicMainReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqTopicMainReceiver.class);

    @RabbitHandler
    public void process(Map message) {
        logger.info("RabbitMQ<--- RabbitmqTopicMainReceiver 消费者收到消息:{}", message.toString());
    }
}
