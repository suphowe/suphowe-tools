package com.soft.redis.defines;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Redis配置类
 * @author suphowe
 */
@Component
@Data
@ToString
@PropertySource(value= {"classpath:properties/redis.properties"})
@ConfigurationProperties(prefix = "spring.redis")
@AllArgsConstructor
@NoArgsConstructor
public class RedisProperties {

    /**
     * spring.redis.database=0
     * spring.redis.host=127.0.0.1
     * spring.redis.port=6379
     * spring.redis.ssl=false
     * spring.redis.password=
     * spring.redis.connTimeout=5000ms
     * spring.redis.maxActive=500
     * spring.redis.maxIdle=10
     * spring.redis.minIdle=0
     * spring.redis.maxWait=5000ms
     * */
    private Integer database;
    private String host;
    private Integer port;
    private Boolean ssl;
    private String password;
    private Long connTimeout;
    private Integer maxActive;
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxWait;
}
