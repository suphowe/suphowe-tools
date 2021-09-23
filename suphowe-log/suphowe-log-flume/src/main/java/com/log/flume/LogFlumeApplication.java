package com.log.flume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * logback + flume 日志集成
 * @author suphowe
 */
@SpringBootApplication
public class LogFlumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogFlumeApplication.class, args);
    }

}
