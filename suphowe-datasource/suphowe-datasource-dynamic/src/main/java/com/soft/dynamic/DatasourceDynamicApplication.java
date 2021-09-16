package com.soft.dynamic;

import com.soft.dynamic.datasource.DatasourceConfigCache;
import com.soft.dynamic.datasource.DatasourceConfigContextHolder;
import com.soft.dynamic.mapper.DatasourceConfigMapper;
import com.soft.dynamic.model.DatasourceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * 动态切换数据源
 * @author suphowe
 */
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DatasourceDynamicApplication implements CommandLineRunner {

    private final DatasourceConfigMapper configMapper;

    public static void main(String[] args) {
        SpringApplication.run(DatasourceDynamicApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 使用默认数据源查询数据源配置列表，将其缓存到 `DatasourceConfigCache` 里，以供后续使用
        // 设置默认的数据源
        DatasourceConfigContextHolder.setDefaultDatasource();
        // 查询所有数据库配置列表
        List<DatasourceConfig> datasourceConfigs = configMapper.selectAll();
        log.info("加载其余数据源配置列表:{} ", datasourceConfigs);
        // 将数据库配置加入缓存
        datasourceConfigs.forEach(config -> DatasourceConfigCache.INSTANCE.addConfig(config.getId(), config));
    }
}
