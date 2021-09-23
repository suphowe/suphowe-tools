package com.hadoop.hbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HBase 启动类
 * @author suphowe
 */
@SpringBootApplication
public class HadoopHbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(HadoopHbaseApplication.class, args);
    }

}
