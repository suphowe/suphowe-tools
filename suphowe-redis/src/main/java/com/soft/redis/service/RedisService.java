package com.soft.redis.service;

import com.soft.redis.dao.RedisDao;
import com.soft.redis.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Redis测试service
 * @author suphowe
 */
@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private RedisUtil redisUtil;

    public void redisPoolTest(){
        redisDao.test();
    }

    public HashMap<String, Object> redisProducer(String key, String value) {
        logger.info("[Redis测试-队列]生产 key:{}  value:{}", key, value);
        HashMap<String, Object> result = new HashMap<>(2);
        redisDao.redisProducer(key, value);
        result.put("CODE", 1);
        result.put("MSG", "success");
        return result;
    }

    public void redisConsumer() {
        String consumerData = redisDao.getRedisConsumerData("redis");
        if(consumerData != null){
            logger.info("[Redis测试-队列]消费，获取消息:{}", consumerData);
        }
        List<Object> redisData = redisDao.getAllRedisConsumerData("redis");
        if(redisData.size()>0){
            redisConsumer();
        }
    }

    public HashMap<String, Object> redisSetHeatValue(String key, String value, String time) {
        HashMap<String, Object> result = new HashMap<>(1);
        redisDao.redisSetHeatValue(key, value, Integer.parseInt(time));
        result.put("msg", "success");
        return result;
    }

    public HashMap<String, Object> redisGetKeyValue(String key) {
        HashMap<String, Object> result = new HashMap<>(2);
        result.put("Data", redisDao.redisGetKeyValue(key));
        return result;
    }

    public HashMap<String, Object> redisGet(String key) {
        HashMap<String, Object> result = new HashMap<>(2);
        result.put("Data", redisDao.redisGet(key));
        return result;
    }

    public HashMap<String, Object> deleteValue(String key) {
        HashMap<String, Object> result = new HashMap<>(2);
        redisDao.deleteValue(key);
        result.put("msg", "success");
        return result;
    }

    public HashMap<String, Object> flushAll() {
        HashMap<String, Object> result = new HashMap<>(1);
        redisUtil.flushAll();
        result.put("msg", "success");
        return result;
    }
}
