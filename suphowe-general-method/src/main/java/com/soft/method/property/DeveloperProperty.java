package com.soft.method.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 开发人员配置信息
 * .@ConfigurationProperties(prefix = "developer")  application.properties 中前缀为 soft 的配置信息
 * @author suphowe
 */
@Data
@ConfigurationProperties(prefix = "soft")
@Component
public class DeveloperProperty {
    private String version;
    private String name;
    private String phone;
}
