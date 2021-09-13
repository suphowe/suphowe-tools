package com.soft.redis.dao;

import com.soft.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis数据层使用
 * @author suphowe
 */
@Component
public class RedisDao {

    @Autowired
    private RedisUtil redisUtil;

    public void test() {
        redisUtil.set("TEST", "" + new Date());
    }

    public void redisProducer(String key, String value) {
        redisUtil.lpush(key, value);
    }

    public String getRedisConsumerData(String key) {
        return (String) redisUtil.brpop(key, 0, TimeUnit.SECONDS);
    }

    public List<Object> getAllRedisConsumerData(String key) {
        return redisUtil.lrange(key, 0, -1);
    }

    public void redisSetHeatValue(String key, String value, int time) {
        redisUtil.set(key, value, time);
    }

    public Set<String> redisGetKeyValue(String key) {
        return redisUtil.keys(key);
    }

    public String redisGet(String key) {
        return (String) redisUtil.get(key);
    }

    /**
     * 模糊删除
     * @param key 键
     */
    public void deleteValue(String key) {
        redisUtil.delVague(key);
    }
}
