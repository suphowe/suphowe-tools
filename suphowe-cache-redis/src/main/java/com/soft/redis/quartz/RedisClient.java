package com.soft.redis.quartz;

import com.soft.redis.service.RedisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Redis，监听Redis队列
 * @author suphowe
 */
@Component
public class RedisClient {

    @Resource
    private RedisService redisServiceImpl;

    @Scheduled(cron = "*/30 * * * * ?")
    public void redisConsumer(){
        redisServiceImpl.redisConsumer();
    }
}
