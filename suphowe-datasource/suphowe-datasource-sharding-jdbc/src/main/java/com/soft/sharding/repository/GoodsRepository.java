package com.soft.sharding.repository;

import com.soft.sharding.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 存储库
 * @author suphowe
 */
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    /**
     * 范围查询
     * @param goodsId1 条件1
     * @param goodsId2 条件2
     * @return 查询结果
     */
    List<Goods> findAllByGoodsIdBetween(Long goodsId1, Long goodsId2);

    /**
     * 通过id进行查询
     * @param goodsIds id list
     * @return 查询结果
     */
    List<Goods> findAllByGoodsIdIn(List<Long> goodsIds);
}
