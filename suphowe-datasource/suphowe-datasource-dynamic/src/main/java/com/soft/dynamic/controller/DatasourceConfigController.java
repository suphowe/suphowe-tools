package com.soft.dynamic.controller;

import com.soft.dynamic.annotation.DefaultDatasource;
import com.soft.dynamic.datasource.DatasourceConfigCache;
import com.soft.dynamic.mapper.DatasourceConfigMapper;
import com.soft.dynamic.model.DatasourceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据源配置 Controller
 * @author suphowe
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DatasourceConfigController {

    private final DatasourceConfigMapper configMapper;

    /**
     * 保存
     */
    @PostMapping("/config")
    @DefaultDatasource
    public DatasourceConfig insertConfig(@RequestBody DatasourceConfig config) {
        configMapper.insertUseGeneratedKeys(config);
        DatasourceConfigCache.INSTANCE.addConfig(config.getId(), config);
        return config;
    }

    /**
     * 保存
     */
    @DeleteMapping("/config/{id}")
    @DefaultDatasource
    public void removeConfig(@PathVariable Long id) {
        configMapper.deleteByPrimaryKey(id);
        DatasourceConfigCache.INSTANCE.removeConfig(id);
    }
}
