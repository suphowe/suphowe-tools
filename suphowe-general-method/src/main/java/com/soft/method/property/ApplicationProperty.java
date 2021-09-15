package com.soft.method.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 项目配置
 * @author suphowe
 */
@Data
@Component
public class ApplicationProperty {

    @Value("${application.name}")
    private String name;
    @Value("${soft.version}")
    private String version;
}
