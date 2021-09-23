package com.hadoop.sqoop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * sqoop 传统数据库和hadoop之间传输数据
 * @author suphowe
 */
@SpringBootApplication
public class HadoopSqoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(HadoopSqoopApplication.class, args);
    }

}
