package com.soft.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * redis启动类
 * EnableScheduling 开启定时任务
 * EnableCaching 开启redis
 * @author suphowe
 */
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class RedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
