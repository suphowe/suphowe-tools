package com.soft.elasticsearch.controller;

import com.soft.elasticsearch.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ElasticSearch控制器
 *
 * @author suphowe
 */
@RestController
@RequestMapping("/elasticSearch")
public class ElasticSearchController {

    @Autowired
    ElasticSearchService elasticSearchService;

    /**
     * 新增索引
     */
    @PostMapping("/saveIndex")
    public void saveIndex(String index, String id, String name, String age) {
        elasticSearchService.saveIndex(index, id, name, age);
    }

    /**
     * 根据索引 id检索
     */
    @GetMapping("/searchIndexId")
    public Map<String, Object> searchIndexId(String indices, String id) {
        return elasticSearchService.searchIndexId(indices, id);
    }

    /**
     * 根据索引检索
     */
    @GetMapping("/searchIndexAll")
    public List<Map<String, Object>> searchIndexAll(String indices) {
        return elasticSearchService.searchIndexAll(indices);
    }

    @GetMapping("/matchSearch")
    public List<Map<String, Object>> matchSearch(String indices, String fieldName, String value) {
        return elasticSearchService.matchSearch(indices, fieldName, value);
    }

    /**
     * 模糊查询
     */
    @GetMapping("/fuzzySearch")
    public List<Map<String, Object>> fuzzySearch(String indices, String fieldName, String value, int prefix, int maxExpansions) {
        return elasticSearchService.fuzzySearch(indices, fieldName, value, prefix, maxExpansions);
    }

    @GetMapping("/wildcardSearch")
    public List<Map<String, Object>> wildcardSearch(String indices, String fieldName, String value) {
        return elasticSearchService.wildcardSearch(indices, fieldName, value);
    }

    @GetMapping("/regexpSearch")
    public List<Map<String, Object>> regexpSearch(String indices, String fieldName, String value) {
        return elasticSearchService.regexpSearch(indices, fieldName, value);
    }

    @GetMapping("/prefixSearch")
    public List<Map<String, Object>> prefixSearch(String indices, String fieldName, String value) {
        return elasticSearchService.prefixSearch(indices, fieldName, value);
    }

    /**
     * 高亮查询
     */
    @GetMapping("/heightSearch")
    public List<Map<String, Object>> heightSearch(String indices, String fieldName, String value, String heightName, String sortName, int page, int pageSize) {
        return elasticSearchService.heightSearch(indices, fieldName, value, heightName, sortName, page, pageSize);
    }

}

