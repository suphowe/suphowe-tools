package com.soft.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * ehcache缓存配置
 * </br>.@EnableCaching 开启驱动的缓存管理
 * @author suphowe
 */
@SpringBootApplication
@EnableCaching
public class CacheEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheEhcacheApplication.class, args);
    }

}
