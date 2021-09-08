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
 * Mybatis主数据源cafmvs配置
 * 多数据源配置依赖数据源配置
 * @author suphowe
 */
@Configuration
@MapperScan(basePackages ="com.soft.datasource.dao.cafmvs", sqlSessionTemplateRef  = "cafmvsSqlSessionTemplate")
public class MybatisCafmvsDataSourceConfig {

    /**
     * 主数据源 cafmvs数据源
     */
    @Primary
    @Bean("cafmvsSqlSessionFactory")
    public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("cafmvsDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath:mapper/cafmvs/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Primary
    @Bean(name = "cafmvsTransactionManager")
    public DataSourceTransactionManager cafmvsTransactionManager(@Qualifier("cafmvsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "cafmvsSqlSessionTemplate")
    public SqlSessionTemplate cafmvsSqlSessionTemplate(@Qualifier("cafmvsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
