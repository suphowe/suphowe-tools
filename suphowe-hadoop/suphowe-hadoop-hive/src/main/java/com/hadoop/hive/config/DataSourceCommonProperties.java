package com.hadoop.hive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * -扩展连接池，通用配置属性，可应用到所有数据源
 * @author suphowe
 *
 */
@Data
@ConfigurationProperties(prefix = DataSourceCommonProperties.DS, ignoreUnknownFields = false)
public class DataSourceCommonProperties {

    final static String DS = "spring.datasource.commonConfig";

    private int initialSize = 10;
    private int minIdle;
    private int maxIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxOpenPreparedStatements;
    private String filters;

    private String mapperLocations;
    private String typeAliasPackage;
}
