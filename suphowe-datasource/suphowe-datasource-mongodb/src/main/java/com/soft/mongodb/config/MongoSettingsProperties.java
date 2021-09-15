package com.soft.mongodb.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * mongodb数据库连接池的配置类
 * @author suphowe
 */
@Data
@Component
public class MongoSettingsProperties {

    private Integer minConnectionsPerHost = 0;
    private Integer maxConnectionsPerHost = 100;
    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
    private Integer serverSelectionTimeout = 30000;
    private Integer maxWaitTime = 120000;
    private Integer maxConnectionIdleTime = 0;
    private Integer maxConnectionLifeTime = 0;
    private Integer connectTimeout = 10000;
    private Integer socketTimeout = 0;
    private Boolean socketKeepAlive = false;
    private Boolean sslEnabled = false;
    private Boolean sslInvalidHostNameAllowed = false;
    private Boolean alwaysUseMBeans = false;
    private Integer heartbeatConnectTimeout = 20000;
    private Integer heartbeatSocketTimeout = 20000;
    private Integer minHeartbeatFrequency = 500;
    private Integer heartbeatFrequency = 10000;
    private Integer localThreshold = 15;
    private String authenticationDatabase;
}
