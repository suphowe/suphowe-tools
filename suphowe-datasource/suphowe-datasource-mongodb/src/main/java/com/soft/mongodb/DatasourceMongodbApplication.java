package com.soft.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * mongodb 数据源整合
 * 去除mongodb的自动配置
 * @author suphowe
 */
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class DatasourceMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceMongodbApplication.class, args);
    }

}
