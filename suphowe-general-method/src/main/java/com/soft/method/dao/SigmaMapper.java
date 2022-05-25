package com.soft.method.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 6sigma 数据生成
 * @author suphowe
 */
@Mapper
public interface SigmaMapper {

    /**
     * 6 sigma 随机数据
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param hours 小时
     * @param millisecond 毫秒
     * @return 插入条数
     */
    int insertSigma(@Param("beginDate") String beginDate,
                    @Param("endDate") String endDate,
                    @Param("hours") String hours,
                    @Param("millisecond") String millisecond);

    /**
     * 清空表
     * @return 清空条数
     */
    int deleteSigmaTable();
}
