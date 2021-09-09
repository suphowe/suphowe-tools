# WebSocket说明

## pom.xml引入
```
        <!-- 增加 websocket-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-messaging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

##  配置文件
MyWebSocketConfig.java

## 核心文件 聊天室
WebSocketServerCluster.java

## html前端  
WebSocketServerCluster.zip  
前端文件需要放入tomcat中运行