package com.ratelimit.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * redis api限流
 * @author suphowe
 */
@SpringBootApplication
public class RatelimitRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatelimitRedisApplication.class, args);
    }

}
