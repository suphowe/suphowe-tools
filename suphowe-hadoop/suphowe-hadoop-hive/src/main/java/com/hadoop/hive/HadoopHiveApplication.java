package com.hadoop.hive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * hive 数据源启动类
 * @author suphowe
 */
@EnableTransactionManagement
@SpringBootApplication
public class HadoopHiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(HadoopHiveApplication.class, args);
    }

}
