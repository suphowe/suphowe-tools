package com.soft.rabbitmq.provider;

import cn.hutool.core.lang.Snowflake;
import com.soft.rabbitmq.defines.RabbitmqProperties;
import com.soft.rabbitmq.system.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

/**
 * RabbitMQ生产者测试
 * @author suphowe
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitmqProviderController {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqProviderController.class);

    /**
     * 使用RabbitTemplate,这提供了接收/发送等等方法
     */
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    private final String messageId = String.valueOf(new Snowflake(2, 3).nextId());

    private final String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(String messageData) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(4);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        logger.info("--------------------------------- 分割线 ---------------------------------");
        logger.info("RabbitMQ===> 直连交换机 RabbitmqProvider sendDirectMessage 发送消息 messageId:{}", messageId);
        logger.info("RabbitMQ===> 直连交换机 RabbitmqProvider sendDirectMessage 发送消息 messageData:{}", messageData);
        logger.info("RabbitMQ===> 直连交换机 RabbitmqProvider sendDirectMessage 发送消息 createTime:{}", createTime);
        logger.info("RabbitMQ===> 将消息携带绑定键值：{} 发送到交换机 {}", rabbitmqProperties.getDirectRouting(), rabbitmqProperties.getDirectExchange());
        rabbitTemplate.convertAndSend(rabbitmqProperties.getDirectExchange(), rabbitmqProperties.getDirectRouting(), map);
        return HttpResult.ok(200);
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1(String messageData) {
        LinkedHashMap<String, Object> manMap = new LinkedHashMap<>(4);
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        logger.info("--------------------------------- 分割线 ---------------------------------");
        logger.info("RabbitMQ===> 主题交换机 RabbitmqProvider sendTopicMessage1 发送消息 messageId:{}", messageId);
        logger.info("RabbitMQ===> 主题交换机 RabbitmqProvider sendTopicMessage1 发送消息 messageData:{}", messageData);
        logger.info("RabbitMQ===> 主题交换机 RabbitmqProvider sendTopicMessage1 发送消息 createTime:{}", createTime);
        logger.info("RabbitMQ===> 主题交换机 {} 发送到队列 {}", rabbitmqProperties.getTopicExchange(), rabbitmqProperties.getMainTopicQueue());
        rabbitTemplate.convertAndSend(rabbitmqProperties.getTopicExchange(), rabbitmqProperties.getMainTopicQueue(), manMap);
        return HttpResult.ok(200);
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2(String messageData) {
        LinkedHashMap<String, Object> womanMap = new LinkedHashMap<>(4);
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        logger.info("--------------------------------- 分割线 ---------------------------------");
        logger.info("RabbitMQ===> 主题交换机 RabbitmqProvider sendTopicMessage2 发送消息 messageId:{}", messageId);
        logger.info("RabbitMQ===> 主题交换机 RabbitmqProvider sendTopicMessage2 发送消息 messageData:{}", messageData);
        logger.info("RabbitMQ===> 主题交换机 RabbitmqProvider sendTopicMessage2 发送消息 createTime:{}", createTime);
        logger.info("RabbitMQ===> 主题交换机 {} 发送到队列 {}", rabbitmqProperties.getTopicExchange(), rabbitmqProperties.getSecondaryTopicQueue());
        rabbitTemplate.convertAndSend(rabbitmqProperties.getTopicExchange(), rabbitmqProperties.getSecondaryTopicQueue(), womanMap);
        return HttpResult.ok(200);
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage(String messageData) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(4);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        logger.info("--------------------------------- 分割线 ---------------------------------");
        logger.info("RabbitMQ===> 扇形交换机 RabbitmqProvider sendFanoutMessage 发送消息 messageId:{}", messageId);
        logger.info("RabbitMQ===> 扇形交换机 RabbitmqProvider sendFanoutMessage 发送消息 messageData:{}", messageData);
        logger.info("RabbitMQ===> 扇形交换机 RabbitmqProvider sendFanoutMessage 发送消息 createTime:{}", createTime);
        rabbitTemplate.convertAndSend(rabbitmqProperties.getFanoutExchange(), null, map);
        return HttpResult.ok(200);
    }

    /**
     * 消息推送到server，但是在server里找不到交换机
     */
    @GetMapping("/sendMessageAckNoExchange")
    public String sendMessageAckNoExchange(String messageData) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(4);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        logger.info("--------------------------------- 分割线 ---------------------------------");
        logger.info("RabbitMQ===> 消息回调测试,发送信息到rabbitmq服务器 交换机不存在 RabbitmqProvider sendMessageAckNoExchange 发送消息 messageId:{}", messageId);
        logger.info("RabbitMQ===> 消息回调测试,发送信息到rabbitmq服务器 交换机不存在 RabbitmqProvider sendMessageAckNoExchange 发送消息 messageData:{}", messageData);
        logger.info("RabbitMQ===> 消息回调测试,发送信息到rabbitmq服务器 交换机不存在 RabbitmqProvider sendMessageAckNoExchange 发送消息 createTime:{}", createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange", "DirectRouting", map);
        return HttpResult.ok(200);
    }

    /**
     * 消息推送到server，存在交换机,找不到队列
     */
    @GetMapping("/sendMessageAckNoQueue")
    public String sendMessageAckNoQueue(String messageData) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(4);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        logger.info("--------------------------------- 分割线 ---------------------------------");
        logger.info("RabbitMQ===> 消息回调测试,发送信息到rabbitmq服务器 队列不存在 RabbitmqProvider sendMessageAckNoQueue 发送消息 messageId:{}", messageId);
        logger.info("RabbitMQ===> 消息回调测试,发送信息到rabbitmq服务器 队列不存在 RabbitmqProvider sendMessageAckNoQueue 发送消息 messageData:{}", messageData);
        logger.info("RabbitMQ===> 消息回调测试,发送信息到rabbitmq服务器 队列不存在 RabbitmqProvider sendMessageAckNoQueue 发送消息 createTime:{}", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "DirectRouting", map);
        return HttpResult.ok(200);
    }

}
