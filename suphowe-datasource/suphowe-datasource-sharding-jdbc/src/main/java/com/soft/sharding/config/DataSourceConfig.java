package com.soft.sharding.config;

import com.soft.sharding.config.DatabaseShardingAlgorithm;
import com.soft.sharding.config.TableShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;
import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;
import com.soft.sharding.database.Database0Config;
import com.soft.sharding.database.Database1Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * data 配置类
 * @author suphowe
 */
@Configuration
public class DataSourceConfig {

    @Resource
    private Database0Config database0Config;

    @Resource
    private Database1Config database1Config;

    @Resource
    private DatabaseShardingAlgorithm databaseShardingAlgorithm;

    @Resource
    private TableShardingAlgorithm tableShardingAlgorithm;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException {
        //分库设置
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        //添加两个数据库database0和database1
        dataSourceMap.put(database0Config.getDatabaseName(), database0Config.createDataSource());
        dataSourceMap.put(database1Config.getDatabaseName(), database1Config.createDataSource());
        //设置默认数据库
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, database0Config.getDatabaseName());

        //分表设置，大致思想就是将查询虚拟表Goods根据一定规则映射到真实表中去
        TableRule orderTableRule = TableRule.builder("goods")
                .actualTables(Arrays.asList("goods_0", "goods_1"))
                .dataSourceRule(dataSourceRule)
                .build();

        //分库分表策略
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("goods_id", databaseShardingAlgorithm))
                .tableShardingStrategy(new TableShardingStrategy("goods_type", tableShardingAlgorithm)).build();
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return dataSource;
    }


    @Bean
    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }

}

