package com.soft.rabbitmq.system;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * 消费者手动确认消息接收
 * @author suphowe
 */
@Component
public class ReceiverAck implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverAck.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            //可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            String[] msgArray = msg.split("'");

            LinkedHashMap<String, String> msgMap = conversionMapString(msgArray[1].trim(), 3);
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");

            logger.info("RabbitMQ<--- ReceiverAck onMessage 接受消息 消费的消息来自的队列名：{}", message.getMessageProperties().getConsumerQueue());
            logger.info("RabbitMQ<--- ReceiverAck onMessage 接受消息 messageId：{}", messageId);
            logger.info("RabbitMQ<--- ReceiverAck onMessage 接受消息 messageData：{}", messageData);
            logger.info("RabbitMQ<--- ReceiverAck onMessage 接受消息 createTime：{}", createTime);

            //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            //第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
            channel.basicReject(deliveryTag, true);
            logger.error("RabbitMQ 消费发生异常:", e);
        }
    }

    /**
     * 将map string转换为map {key=value,key=value,key=value} 格式转换成map
     * @param mapString map string
     * @param maxArrayLength 数组的最大长度
     * @return map
     */
    private LinkedHashMap<String, String> conversionMapString(String mapString, int maxArrayLength) {
        mapString = mapString.substring(1, mapString.length() - 1);
        String[] strs = mapString.split(",", maxArrayLength);
        LinkedHashMap<String, String> map = new LinkedHashMap<>(4);
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
