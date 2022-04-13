package com.soft.elasticsearch.utils;

import cn.hutool.core.util.StrUtil;
import com.soft.elasticsearch.config.ElasticSearchConfig;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ElasticSearch 工具类
 * @author suphowe
 */
@Component
public class ElasticSearchUtil {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUtil.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     *  保存文档
     * @param indices 索引
     * @param type 类型-表
     * @param id 文档ID
     * @param jsonData Json数据
     */
    public void saveIndex(String indices, String type, String id, String jsonData) {
        try {
            IndexRequest indexRequest = new IndexRequest(indices);
            if (!StrUtil.hasBlank(type)) {
                indexRequest.type(type);
            }
            indexRequest.id(id);
            indexRequest.source(jsonData, XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);
            logger.info("保存数据, 返回:{}", indexResponse);
        } catch (Exception e) {
            logger.error("发生异常:", e);
        }
    }

    /**
     * 删除索引
     * @param indices 索引
     * @throws Exception IO异常
     */
    public boolean deleteIndex(String indices) throws Exception {
        DeleteIndexRequest request = new DeleteIndexRequest(indices);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, ElasticSearchConfig.COMMON_OPTIONS);
        return delete.isAcknowledged();
    }

    /**
     * 获取指定索引下的 id 文档
     * @param indices 索引
     * @param id id
     */
    public Map<String, Object> searchIndexId(String indices, String id) {
        try {
            GetRequest getRequest = new GetRequest(indices);
            getRequest.id(id);
            GetResponse response = restHighLevelClient.get(getRequest, ElasticSearchConfig.COMMON_OPTIONS);
            Map<String, Object> source = response.getSource();
            logger.info("ElasticSearch检索的信息：{}", source);
            return source;
        } catch (Exception e) {
            logger.error("发生异常:", e);
            return null;
        }
    }

    /**
     * 查询索引下所有数据
     * @param indices 索引名称
     * @param page 页码
     * @param pageSize 每页数量
     */
    public List<Map<String, Object>> matchAll(String indices, Integer page, Integer pageSize) {
        try {
            SearchRequest searchRequest=new SearchRequest(indices);
            // 创建查询条件构建器SearchSourceBuilder
            SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
            // 查询条件
            QueryBuilder queryBuilder= QueryBuilders.matchAllQuery();
            // 指定查询条件
            sourceBuilder.query(queryBuilder);
            // 添加查询条件构建器 SearchSourceBuilder
            searchRequest.source(sourceBuilder);
            // 添加分页信息  不设置 默认10条
            if (page != null && pageSize != null){
                sourceBuilder.from(page);
                sourceBuilder.size(pageSize);
            }
            // 查询,获取查询结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("matchAll查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("matchAll查询发生异常:", e);
            return null;
        }
    }

    /**
     * term查询
     * @param indices 索引
     * @param fieldName 字段名称
     * @param value 字段值
     */
    public List<Map<String, Object>> termSearch(String indices, String fieldName, String value) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            QueryBuilder queryBuilder= QueryBuilders.termQuery(fieldName, value);
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("term查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("term查询发生异常:", e);
            return null;
        }
    }

    /**
     * match查询
     * @param indices 索引
     * @param matchName 字段名称
     * @param matchText 字段值
     */
    public List<Map<String, Object>> matchSearch(String indices, String matchName, Object matchText) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchName, matchText);
            searchSourceBuilder.query(matchQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("match查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("match查询发生异常:", e);
            return null;
        }
    }

    /**
     * wildcard模糊查询
     * @param indices 索引
     * @param fieldName 字段名称
     * @param value 字段值
     */
    public List<Map<String, Object>> wildcardSearch(String indices, String fieldName, String value) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder(fieldName, value);
            searchSourceBuilder.query(wildcardQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("wildcard模糊查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("wildcard模糊查询发生异常:", e);
            return null;
        }
    }

    /**
     * 正则查询
     * @param indices 索引
     * @param fieldName 字段名称
     * @param value 正则表达式
     */
    public List<Map<String, Object>> regexpSearch(String indices, String fieldName, String value) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            RegexpQueryBuilder regexpQueryBuilder = QueryBuilders.regexpQuery(fieldName, value);
            searchSourceBuilder.query(regexpQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("正则查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("正则查询发生异常:", e);
            return null;
        }
    }

    /**
     * 前缀查询
     * @param indices 索引
     * @param fieldName 字段名称
     * @param value 前缀
     */
    public List<Map<String, Object>> prefixSearch(String indices, String fieldName, String value) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery(fieldName, value);
            searchSourceBuilder.query(prefixQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("前缀查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("前缀查询发生异常:", e);
            return null;
        }
    }

    /**
     * fuzzy模糊查询
     * @param indices 索引
     * @param fieldName 字段名称
     * @param value 字段值
     * @param prefix 模糊前缀
     * @param maxExpansions 最大扩展选项
     */
    public List<Map<String, Object>> fuzzySearch(String indices, String fieldName, String value, Integer prefix, Integer maxExpansions) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldName, value);
            //开启模糊性查询
            matchQueryBuilder.fuzziness(Fuzziness.AUTO);
            //模糊前缀
            if (prefix != null) {
                matchQueryBuilder.prefixLength(prefix);
            }
            //设置最大扩展选项
            if (maxExpansions != null) {
                matchQueryBuilder.maxExpansions(maxExpansions);
            }
            searchSourceBuilder.query(matchQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("fuzzy模糊查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("fuzzy模糊查询发生异常:", e);
            return null;
        }
    }

    /**
     * 范围排序查询
     * @param indices 索引
     * @param fieldName 字段名称
     * @param gte 指定下限
     * @param lte 指定上限
     * @param order 排序方式,从SortOrder获取
     */
    public List<Map<String, Object>> rangeSearch(String indices, String fieldName, Object gte, Object lte, SortOrder order) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(fieldName);
            //指定下限
            rangeQueryBuilder.gte(gte);
            //指定上限
            rangeQueryBuilder.lte(lte);
            searchSourceBuilder.query(rangeQueryBuilder);
            //排序 desc
            searchSourceBuilder.sort(fieldName, order);

            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("范围查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("范围查询发生异常:", e);
            return null;
        }
    }

    /**
     * query_string查询
     * @param indices 索引
     * @param queryString 字段名称
     * @param fields 查询方式
     * @param queryType 查询方式
     * @param operator 连接符
     */
    public List<Map<String, Object>> queryStringSearch(String indices, String queryString, String[] fields,String queryType, Operator operator) {
        try {
            //创建搜索请求。如果没有参数，这将对所有索引运行。
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            //大多数搜索参数都添加到SearchSourceBuilder中。它为进入搜索请求主体的所有内容提供了setter。
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            if ("query_string".equals(queryType)) {
                QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(queryString);
                if (!StrUtil.hasBlank(fields)){
                    for (String field : fields) {
                        queryStringQueryBuilder.field(field);
                    }
                }
                queryStringQueryBuilder.defaultOperator(operator);
                searchSourceBuilder.query(queryStringQueryBuilder);
            }
            if ("simple_query_string".equals(queryType)) {
                SimpleQueryStringBuilder simpleQueryStringQuery = QueryBuilders.simpleQueryStringQuery(queryString);
                if (!StrUtil.hasBlank(fields)){
                    for (String field : fields) {
                        simpleQueryStringQuery.field(field);
                    }
                }
                simpleQueryStringQuery.defaultOperator(operator);
                searchSourceBuilder.query(simpleQueryStringQuery);
            }
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("query_string查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("query_string查询发生异常:", e);
            return null;
        }
    }

    /**
     * 布尔查询
     * @param indices 索引
     * @param termName term字段名称
     * @param termValue term值
     * @param matchName match字段名称
     * @param matchText match值
     * @param rangeName range字段名称
     * @param gte 指定下限
     * @param lte 指定上限
     */
    public List<Map<String, Object>> boolSearch(String indices, String termName, String termValue,
                                                String matchName, Object matchText, String rangeName,
                                                Object gte, Object lte) {
        try {
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 构建boolQuery
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            // 构建各个查询条件 must 、filter为连接方式
            // term查询
            if (!StrUtil.hasBlank(termName) && !StrUtil.hasBlank(termValue)) {
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(termName, termValue);
                boolQueryBuilder.must(termQueryBuilder);
            }
            // match查询
            if (!StrUtil.hasBlank(matchName) && matchText != null) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchName, matchText);
                boolQueryBuilder.filter(matchQueryBuilder);
            }
            // 区间查询
            if (!StrUtil.hasBlank(rangeName) && gte != null && lte != null) {
                RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(rangeName);
                rangeQuery.gte(gte);
                rangeQuery.lte(lte);
                boolQueryBuilder.filter(rangeQuery);
            }

            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("布尔查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("布尔查询发生异常:", e);
            return null;
        }
    }

    /**
     * 聚合查询
     * @param indices 索引
     * @param matchName match字段名称
     * @param matchText match值
     * @param termName 组名称
     * @param fieldName 查询字段
     * @param size 查询条数
     */
    public List<Map<String, Object>> aggSearch(String indices, String matchName, String matchText, String termName,
                                                String fieldName, int size) {
        try {
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchName, matchText);
            searchSourceBuilder.query(matchQueryBuilder);
            // 分组
            AggregationBuilder aggregationBuilder = AggregationBuilders.terms(termName).field(fieldName).size(size);
            searchSourceBuilder.aggregation(aggregationBuilder);

            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            logger.info("聚合查询获取结果:{}", result);
            return result;
        } catch (Exception e) {
            logger.error("聚合查询发生异常:", e);
            return null;
        }
    }

    /**
     * 高亮查询
     * @param indices 索引
     * @param matchName 字段名称
     * @param matchText 字段值
     * @param heightName 高亮名称
     * @param sortName 排序名称
     * @param page 页码
     * @param pageSize 命中
     */
    public List<Map<String, Object>> heightSearch(String indices, String matchName, Object matchText, String heightName, String sortName, Integer page, Integer pageSize) {
        try {
            //指定搜素请求信息
            SearchRequest searchRequest;
            if (StrUtil.hasBlank(indices)) {
                searchRequest = new SearchRequest();
            } else {
                searchRequest = new SearchRequest(indices);
            }
            // 创建搜素源生成器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 匹配
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(matchName, matchText);
            searchSourceBuilder.query(matchQueryBuilder);
            // 高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置三要素
            highlightBuilder.field(heightName);
            // 设置前后缀标签
            highlightBuilder.preTags("<font color='red'>");
            highlightBuilder.postTags("</font>");
            // 加载已经设置好的高亮配置
            searchSourceBuilder.highlighter(highlightBuilder);

            //设置排序
            if (!StrUtil.hasBlank(sortName)) {
                searchSourceBuilder.sort(sortName);
            }
            //设置分页
            if (page != null && pageSize != null){
                // 页码
                searchSourceBuilder.from(page);
                // 默认命中10
                searchSourceBuilder.size(pageSize);
            }
            searchRequest.source(searchSourceBuilder);

            SearchResponse search = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            List<Map<String, Object>> result = new ArrayList<>();
            for (SearchHit hit : search.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                result.add(sourceAsMap);
            }
            return result;
        } catch (Exception e) {
            logger.error("发生异常:", e);
            return null;
        }
    }
}
