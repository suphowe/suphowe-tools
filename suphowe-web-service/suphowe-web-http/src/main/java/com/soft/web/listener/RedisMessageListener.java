package com.soft.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * redis 消息监听器
 * @author suphowe
 */
@Component
public class RedisMessageListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RedisSerializer<String> stringSerializer = new StringRedisSerializer();

    /**
     * key过期会执行这个方法
     * @param message 消息
     * @param pattern 表达式
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = stringSerializer.deserialize(message.getBody());
        logger.info("RedisMessageListener, key{}", key);
    }

}
