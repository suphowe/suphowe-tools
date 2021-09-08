package com.soft.datasource.config.template;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JdbcTemplate多数据源配置
 * 依赖于数据源配置
 * @author suphowe
 */
@Configuration
public class JdbcTemplateDataSourceConfig {

    /**
     * JdbcTemplate主数据源local数据源
     */
    @Primary
    @Bean(name = "localJdbcTemplate")
    public JdbcTemplate ds1JdbcTemplate(@Qualifier("localDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * JdbcTemplate主数据源cafmvs数据源
     */
    @Bean(name = "cafmvsJdbcTemplate")
    public JdbcTemplate ds2JdbcTemplate(@Qualifier("cafmvsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
