package com.soft.elasticsearch.service;

import com.google.gson.Gson;
import com.soft.elasticsearch.utils.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ElasticSearch 服务类
 * @author suphowe
 */
@Service
public class ElasticSearchService {

    @Autowired
    private ElasticSearchUtil elasticSearchUtil;

    public void saveIndex(String index, String id, String name, String age) {
        HashMap<String, Object> param = new HashMap<>(4);
        param.put("name", name);
        param.put("age", 30);
        elasticSearchUtil.saveIndex("user", null, id, new Gson().toJson(param));
    }

    public Map<String, Object> searchIndexId(String indices, String id) {
        return elasticSearchUtil.searchIndexId(indices, id);
    }

    public List<Map<String, Object>> matchSearch(String indices, String fieldName, String value) {
        return elasticSearchUtil.matchSearch(indices, fieldName, value);
    }

    public List<Map<String, Object>> searchIndexAll(String indices) {
        return elasticSearchUtil.matchAll(indices, null, null);
    }

    public List<Map<String, Object>> fuzzySearch(String indices, String fieldName, String value, int prefix, int maxExpansions) {
        return elasticSearchUtil.fuzzySearch(indices, fieldName, value, prefix, maxExpansions);
    }

    public List<Map<String, Object>> wildcardSearch(String indices, String fieldName, String value) {
        return elasticSearchUtil.wildcardSearch(indices, fieldName, value);
    }

    public List<Map<String, Object>> regexpSearch(String indices, String fieldName, String value) {
        return elasticSearchUtil.regexpSearch(indices, fieldName, value);
    }

    public List<Map<String, Object>> prefixSearch(String indices, String fieldName, String value) {
        return elasticSearchUtil.prefixSearch(indices, fieldName, value);
    }

    public List<Map<String, Object>> heightSearch(String indices, String fieldName, String value, String heightName, String sortName, int page, int pageSize) {
        return elasticSearchUtil.heightSearch(indices, fieldName, value, heightName, sortName, page, pageSize);
    }
}
