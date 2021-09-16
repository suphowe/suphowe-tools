package com.soft.dynamic.mapper;

import com.soft.dynamic.config.MyMapper;
import com.soft.dynamic.model.DatasourceConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 * @author suphowe
 */
@Mapper
public interface DatasourceConfigMapper extends MyMapper<DatasourceConfig> {
}
