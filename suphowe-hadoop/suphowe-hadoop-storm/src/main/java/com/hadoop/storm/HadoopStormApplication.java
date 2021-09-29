package com.hadoop.storm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * storm 启动类
 * @author suphowe
 */
@Slf4j
@SpringBootApplication
public class HadoopStormApplication {

    private static ConfigurableApplicationContext context = null;

    public static synchronized void run(String... args) {
        if (context == null) {
            context = SpringApplication.run(HadoopStormApplication.class, args);
        }
    }
}
