package com.soft.oracle.dao;

import com.soft.oracle.entity.MidEmail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MID_EMAIL 表操作
 * @author suphowe
 */
@Mapper
@Component
public interface MidEmailMapper {

    /**
     * 查询前100行数据
     */
    List<MidEmail> queryFirst100();
}
