package com.hadoop.mapreduce.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * HDFS 配置类
 * @author suphowe
 */
@Data
@Configuration
@PropertySource(value= {"classpath:properties/hdfs.properties"})
public class HdfsConfig {

    @Value("${hdfs.path")
    String path;
}
