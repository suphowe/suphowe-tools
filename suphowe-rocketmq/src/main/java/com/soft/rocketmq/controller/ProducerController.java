package com.soft.rocketmq.controller;

import cn.hutool.core.lang.Snowflake;
import com.google.gson.Gson;
import com.soft.rocketmq.producer.config.Producer;
import com.soft.rocketmq.producer.defines.ProducerProperties;
import com.soft.rocketmq.utils.HttpResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

/**
 * rocketmq 测试
 * @author suphowe
 */
@Slf4j
@Controller
@RequestMapping("/rocketmq")
@Api(value = "rocketmq测试")
public class ProducerController {

    @Autowired
    Producer producer;

    @Autowired
    ProducerProperties producerProperties;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(String messageData) throws Exception {
        Snowflake snowflake = new Snowflake(2, 3);
        String messageId = String.valueOf(snowflake.nextId());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LinkedHashMap<String, Object> map = new LinkedHashMap<>(4);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        log.info("--------------------------------- 分割线 ---------------------------------");
        log.info("RocketMq===> RocketmqProducer sendDirectMessage 发送消息 messageId:{}", messageId);
        log.info("RocketMq===> RocketmqProducer sendDirectMessage 发送消息 messageData:{}", messageData);
        log.info("RocketMq===> RocketmqProducer sendDirectMessage 发送消息 createTime:{}", createTime);
        log.info("RocketMq===> 将生产者Topic：{} 发送到tag {}", producerProperties.getProducerTopic(), producerProperties.getProducerTag());

        //创建生产信息
        Message message = new Message(producerProperties.getProducerTopic(), producerProperties.getProducerTag(), new Gson().toJson(map).getBytes());
        //发送
        SendResult sendResult = producer.getProducer().send(message);
        log.info("输出生产者信息={}",sendResult);
        return HttpResult.ok(200);
    }
}
