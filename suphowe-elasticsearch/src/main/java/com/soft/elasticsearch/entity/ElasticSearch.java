package com.soft.elasticsearch.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * ElasticSearch 配置
 * @author suphowe
 */
@Data
public class ElasticSearch {

    @Value("${spring.elasticsearch.es1.hostname}")
    String hostname;

    @Value("${spring.elasticsearch.es1.port}")
    int port;

    @Value("${spring.elasticsearch.es1.scheme}")
    String scheme;
}
