package com.hadoop.hive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 统一属性控制类，获取配置文件属性
 * @author suphowe
 *
 */
@Data
@ConfigurationProperties(prefix = DataSourceProperties.DS, ignoreUnknownFields = false)
public class DataSourceProperties {

    final static String DS = "spring.datasource";

    private Map<String,String> hive;

    private Map<String,String> commonConfig;

}
