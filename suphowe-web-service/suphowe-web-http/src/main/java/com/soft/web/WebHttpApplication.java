package com.soft.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * web服务启动
 * @author suphowe
 */
@EnableCaching
@SpringBootApplication
public class WebHttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebHttpApplication.class, args);
    }

}
