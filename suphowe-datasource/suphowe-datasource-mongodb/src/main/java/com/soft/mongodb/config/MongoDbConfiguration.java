package com.soft.mongodb.config;

import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * mongodb 配置
 * @author suphowe
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.primary.mongodb")
public class MongoDbConfiguration {

    // MongoDB Properties

    private ArrayList<String> addresses;
    private String database, username, password;
    private int port;

    @Autowired
    MongoSettingsProperties properties;

    /**
     * 覆盖容器中默认的MongoDbFacotry Bean
     */
    public MongoDbFactory mongoDbFactory() {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(properties.getMaxConnectionsPerHost());
        builder.minConnectionsPerHost(properties.getMinConnectionsPerHost());
        builder.threadsAllowedToBlockForConnectionMultiplier(properties.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.serverSelectionTimeout(properties.getServerSelectionTimeout());
        builder.maxWaitTime(properties.getMaxWaitTime());
        builder.maxConnectionIdleTime(properties.getMaxConnectionIdleTime());
        builder.maxConnectionLifeTime(properties.getMaxConnectionLifeTime());
        builder.connectTimeout(properties.getConnectTimeout());
        builder.socketTimeout(properties.getSocketTimeout());
        // builder.socketKeepAlive(properties.getSocketKeepAlive());
        builder.sslEnabled(properties.getSslEnabled());
        builder.sslInvalidHostNameAllowed(properties.getSslInvalidHostNameAllowed());
        builder.alwaysUseMBeans(properties.getAlwaysUseMBeans());
        builder.heartbeatFrequency(properties.getHeartbeatFrequency());
        builder.minHeartbeatFrequency(properties.getMinHeartbeatFrequency());
        builder.heartbeatConnectTimeout(properties.getHeartbeatConnectTimeout());
        builder.heartbeatSocketTimeout(properties.getHeartbeatSocketTimeout());
        builder.localThreshold(properties.getLocalThreshold());
        MongoClientOptions mongoClientOptions = builder.build();

        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
        for (String address : addresses) {
            String[] hostAndPort = address.split(":");
            String host = hostAndPort[0];
            Integer port = Integer.parseInt(hostAndPort[1]);
            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }

        // 连接认证
        // MongoCredential mongoCredential = null;
        // 	mongoCredential = MongoCredential.createScramSha1Credential(MongoCredential.createCredential(username, database, password.toCharArray()));
        // 创建认证客户端
        // MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);

        // 创建非认证客户端
//        MongoClient mongoClient = new MongoClient(serverAddresses, mongoClientOptions);
//
//        // 创建MongoDbFactory
//        MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(mongoClient, database);
        return null;
    }

    /**
     * 第一个数据库 默认作为主数据库 需要添加注解 @Primary ，后面的数据库不需要这个注解
     */
    @Primary
    @Bean(name = "LocalMongoDbTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
