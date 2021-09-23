package com.hadoop.mahout.controller;

import com.hadoop.mahout.entity.Item;
import com.hadoop.mahout.service.RecommendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品推荐
 * @author suphowe
 */
@RestController
public class RecommendController {

    @Resource
    RecommendService recommendService;

    @RequestMapping("recommendByUser")
    public List<Item> getRecommendItemsByUser(Long userId, int num){
        List<Item> items= recommendService.getRecommendItemsByUser(userId,num);
        return items;
    }

    @RequestMapping("recommendByItem")
    public List<Item> getRecommendItemsByItem(Long userId,Long itemId, int num){
        List<Item> items= recommendService.getRecommendItemsByItem(userId,itemId, num);
        return items;
    }
}