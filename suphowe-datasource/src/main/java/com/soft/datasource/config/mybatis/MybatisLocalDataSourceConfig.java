package com.soft.datasource.config.mybatis;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Mybatis主数据源ds1配置
 * 多数据源配置依赖数据源配置
 * @author suphowe
 */
@Configuration
@MapperScan(basePackages ="com.soft.datasource.dao.local", sqlSessionTemplateRef  = "localSqlSessionTemplate")
public class MybatisLocalDataSourceConfig {

    /**
     * 主数据源 local数据源
     */
    @Primary
    @Bean("localSqlSessionFactory")
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("localDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath:mapper/local/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Primary
    @Bean(name = "localTransactionManager")
    public DataSourceTransactionManager localTransactionManager(@Qualifier("localDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "localSqlSessionTemplate")
    public SqlSessionTemplate localSqlSessionTemplate(@Qualifier("localSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
