package com.soft.rocketmq.service;

import com.soft.rocketmq.producer.config.Producer;
import com.soft.rocketmq.producer.defines.ProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生产者发送消息
 * @author suphowe
 */
@Slf4j
@Service
public class ProducerService {

    @Autowired
    private Producer producer;

    private List<String> mesList;

    @Autowired
    private ProducerProperties producerProperties;

    public String sendMsg(String message) throws Exception {
        //创建生产信息
        Message sendData = new Message(producerProperties.getProducerTopic(), "testtag", message.getBytes());
        //发送
        SendResult sendResult = producer.getProducer().send(sendData);
        log.info("输出生产者信息={}", sendResult);
        return "success!";
    }
}
