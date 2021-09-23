package com.hadoop.mahout.service;

import com.hadoop.mahout.entity.Item;

import java.util.List;

/**
 * 推荐接口
 * @author suphowe
 */
public interface RecommendService {
    /**
     * 基于用户的商品推荐
     * @param userId 用户id
     * @param howMany 推荐数量
     * @return 推荐的商品
     */
    List<Item> getRecommendItemsByUser(Long userId, int howMany);

    /**
     * 基于内容的商品推荐
     * @param userId 用户id
     * @param itemId 商品id
     * @param howMany 推荐数量
     * @return 推荐的商品
     */
    List<Item> getRecommendItemsByItem(Long userId, Long itemId, int howMany);
}
