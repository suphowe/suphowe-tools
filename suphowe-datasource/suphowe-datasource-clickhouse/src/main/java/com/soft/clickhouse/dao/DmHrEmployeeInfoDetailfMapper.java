package com.soft.clickhouse.dao;

import com.soft.clickhouse.entity.DmHrEmployeeInfoDetailf;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author suphowe
 */
@Mapper
@Component
public interface DmHrEmployeeInfoDetailfMapper {

    /**
     * 查看所有数据
     * @return 所有操作数据
     */
    List<DmHrEmployeeInfoDetailf> queryFirst100();
}
