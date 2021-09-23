package com.hadoop.mahout.service.impl;

import com.hadoop.mahout.dao.ItemMapper;
import com.hadoop.mahout.entity.Item;
import com.hadoop.mahout.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 推荐实现类
 * @author suphowe
 */
@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private ItemMapper itemMapper;

    @Resource
    private DataModel dataModel;

    @Override
    public List<Item> getRecommendItemsByUser(Long userId, int howMany) {
        List<Item> list = null;
        try {
            //计算相似度，相似度算法有很多种，采用基于皮尔逊相关性的相似度
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
            //构建推荐器，基于用户的协同过滤推荐
            Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
            long start = System.currentTimeMillis();
            //推荐商品
            List<RecommendedItem> recommendedItemList = recommender.recommend(userId, howMany);
            List<Long> itemIds = new ArrayList<>();
            for (RecommendedItem recommendedItem : recommendedItemList) {
                itemIds.add(recommendedItem.getItemID());
            }
            log.info("推荐出来的商品id集合:{}", itemIds);

            //根据商品id查询商品
            if(itemIds.size()>0) {
                list = itemMapper.findAllByIds(itemIds);
            }else{
                list = new ArrayList<>();
            }
            log.info("推荐数量:{} 耗时：{}", list.size() , System.currentTimeMillis()-start);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Item> getRecommendItemsByItem(Long userId, Long itemId, int howMany) {

        List<Item> list = null;
        try {
            //计算相似度，相似度算法有很多种，采用基于皮尔逊相关性的相似度
            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
            //4)构建推荐器，使用基于物品的协同过滤推荐
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
            long start = System.currentTimeMillis();
            // 物品推荐相似度，计算两个物品同时出现的次数，次数越多任务的相似度越高。
            List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(userId, itemId, howMany);
            //打印推荐的结果
            List<Long> itemIds = new ArrayList<>();
            for (RecommendedItem recommendedItem : recommendedItemList) {
                itemIds.add(recommendedItem.getItemID());
            }
            log.info("推荐出来的商品id集合:{}", itemIds);

            //根据商品id查询商品
            if(itemIds.size()>0) {
                list = itemMapper.findAllByIds(itemIds);
            }else{
                list = new ArrayList<>();
            }
            log.info("推荐数量:{} 耗时：{}", list.size() , System.currentTimeMillis()-start);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return list;
    }
}
