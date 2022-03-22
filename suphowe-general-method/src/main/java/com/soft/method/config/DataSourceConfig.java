package com.soft.method.config;

import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据库配置类
 * @author suphowe
 */
@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    StringEncryptor stringEncryptor;

    /**
     * 数据源配置
     */
    @Primary
    @Bean(name = "dataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 数据源
     */
    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
        try {
            // 解密数据库配置,重新加载
//            String url = dataSourceProperties.getUrl();
//            String username = dataSourceProperties.getUsername();
//            String password = dataSourceProperties.getPassword();
//            String newUrl = stringEncryptor.decrypt(url);
//            String newUsername = stringEncryptor.decrypt(username);
//            String newPassword = stringEncryptor.decrypt(password);
//            dataSourceProperties.setUrl(newUrl);
//            dataSourceProperties.setUsername(newUsername);
//            dataSourceProperties.setPassword(newPassword);
        } catch (Exception e) {
            logger.info("reload datasource properties failed!");
        }
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
