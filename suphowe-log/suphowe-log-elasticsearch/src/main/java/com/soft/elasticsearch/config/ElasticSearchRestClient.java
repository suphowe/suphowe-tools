package com.soft.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Elasticsearch 配置类
 * @author suphowe
 */
@Configuration
public class ElasticSearchRestClient {

//    /**
//     * ES地址,ip:port
//     */
//    @Value("${spring.elasticsearch.rest.uris}")
//    private String uris;
//
//    @Bean
//    public RestHighLevelClient restHighLevelClient() {
//        String[] address = uris.split(":");
//        String ip = address[0];
//        int port = Integer.parseInt(address[1]);
//        RestClientBuilder builder = RestClient.builder(new HttpHost(ip, port, "http"));
//        return new RestHighLevelClient(builder);
//    }
}