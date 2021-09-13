package com.soft.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义配置
 * @author suphowe
 */
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {

    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
