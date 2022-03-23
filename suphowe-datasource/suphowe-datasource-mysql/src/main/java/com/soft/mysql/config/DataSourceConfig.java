package com.soft.mysql.config;

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
 * 配置类
 * @author suphowe
 */
@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    StringEncryptor stringEncryptor;

    /**
     * 主数据源配置 local数据源
     */
    @Primary
    @Bean(name = "localDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.mysql.datasource.local")
    public DataSourceProperties localDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 主数据源 local数据源
     */
    @Primary
    @Bean(name = "localDataSource")
    public DataSource localDataSource(@Qualifier("localDataSourceProperties") DataSourceProperties dataSourceProperties) {
        try {
            // 解密数据库配置,重新加载
            String url = dataSourceProperties.getUrl();
            String username = dataSourceProperties.getUsername();
            String password = dataSourceProperties.getPassword();
            /*
            String newUrl = stringEncryptor.decrypt(url);
            String newUsername = stringEncryptor.decrypt(username);
            String newPassword = stringEncryptor.decrypt(password);
            dataSourceProperties.setUrl(newUrl);
            dataSourceProperties.setUsername(newUsername);
            dataSourceProperties.setPassword(newPassword);
             */
        } catch (Exception e) {
            logger.info("reload datasource properties failed!");
        }
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * cafmvs数据源配置
     */
    @Bean(name = "cafmvsDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.mysql.datasource.cafmvs")
    public DataSourceProperties cafmvsDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * cafmvs 数据源
     */
    @Bean("cafmvsDataSource")
    public DataSource cafmvsDataSource(@Qualifier("cafmvsDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
