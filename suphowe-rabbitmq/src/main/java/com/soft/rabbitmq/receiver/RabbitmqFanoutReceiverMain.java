package com.soft.rabbitmq.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 扇形消费者
 * 监听的队列名称 fanoutQueue.main
 * @author suphowe
 */
@Component
@RabbitListener(queues = "fanoutQueue.main")
public class RabbitmqFanoutReceiverMain {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqFanoutReceiverMain.class);

    @RabbitHandler
    public void process(Map message) {
        logger.info("RabbitMQ<--- RabbitmqFanoutReceiverMain 消费者收到消息:{}", message.toString());
    }
}
