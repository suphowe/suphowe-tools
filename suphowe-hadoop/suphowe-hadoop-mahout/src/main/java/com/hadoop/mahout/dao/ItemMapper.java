package com.hadoop.mahout.dao;

import com.hadoop.mahout.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 商品数据处理
 * @author suphowe
 */
@Mapper
public interface ItemMapper {

    /**
     * 通过id批量查询
     */
    List<Item> findAllByIds(@Param("Ids") List<Long> Ids);
}
