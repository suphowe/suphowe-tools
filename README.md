# suphowe-tools

## modules

| type          | Module                           | Port | 描述                     |
|:--------------|:---------------------------------|:-----|:-----------------------|
| workflow      | suphowe-activiti                 | 8010 | activiti工作流            |
| workflow      | suphowe-camunda                  | 8011 | camunda工作流             |
| actuator      | suphowe-actuator                 | 8012 | 服务健康检查                 |
| cache         | suphowe-cache-ehcache            | 8020 | ehcache缓存              |
| cache         | suphowe-cache-redis              | 8021 | redis                  |
| cache         | suphowe-cache-session            | 8022 | session                |
| auto-code     | suphowe-code-autocreate          | 8030 | 代码自动生成                 |
| control-admin | suphowe-control-admin-server     | 8035 | 服务健康检查 服务端             |
| control-admin | suphowe-control-admin-client     | 8036 | 服务健康检查 客户端             |
| datasource    | suphowe-datasource-clickhouse    | 8040 | clickhouse 数据处理        |
| datasource    | suphowe-datasource-dynamic       | 8041 | 动态添加/删除`数据源            |
| datasource    | suphowe-datasource-mongodb       | 8042 | mongodb多数据源配置方案        |
| datasource    | suphowe-datasource-mysql         | 8043 | mysql多数据源配置方案          |
| datasource    | suphowe-datasource-oracle        | 8044 | oracle 数据处理            |
| datasource    | suphowe-datasource-pgsql         | 8045 | PgSql数据库集成             |
| datasource    | suphowe-datasource-sharding-jdbc | 8046 | 分库分表数据处理               |
| docker        | suphowe-docker                   | 8050 | docker 使用              |
| email         | suphowe-email                    | 8052 | 邮件                     |
| general       | suphowe-general-method           | 8053 | 通用方法测试                 |
| file          | suphowe-file-view                | 8054 | 文件预览                   |
| hadoop        | suphowe-hadoop-hbase             | 8060 | hadoop hbase数据源集成      |
| hadoop        | suphowe-hadoop-hdfs              | 8061 | hadoop hdfs集成          |
| hadoop        | suphowe-hadoop-hive              | 8062 | hadoop hive数据源集成       |
| hadoop        | suphowe-hadoop-mahout            | 8063 | hadoop mahout 数据挖掘算法库  |
| hadoop        | suphowe-hadoop-mapreduce         | 8064 | hadoop mahout 数据挖掘算法库  |
| hadoop        | suphowe-hadoop-spark             | 8065 | hadoop spark 数据处理      |
| hadoop        | suphowe-hadoop-sqoop             | 8066 | hadoop sqoop 数据处理      |
| hadoop        | suphowe-hadoop-storm             | 8067 | hadoop storm 数据处理      |
| hadoop        | suphowe-hadoop-yarn              | 8068 | hadoop yarn            |
| log           | suphowe-log-elasticsearch        | 8070 | elasticsearch 操作日志     |
| log           | suphowe-log-flume                | 8071 | flume 日志               |
| mq            | rocketmq-console-ng              | 8075 | ng                     |
| mq            | suphowe-mq-rabbitmq              | 8076 | rabbitmq               |
| mq            | suphowe-mq-rocketmq              | 8077 | rocketmq               |
| netty         | suphowe-netty-client             | 8081 | netty客户端               |
| netty         | suphowe-netty-server             | 8082 | netty服务端               |
| ratelimit     | suphowe-ratelimit-guava          | 8085 | guava api 访问限流         |
| ratelimit     | suphowe-ratelimit-redis          | 8086 | redis api 访问限流         |
| rbac          | suphowe-rbac-security            | 8087 | security权限管理           |
| rbac          | suphowe-rbac-shiro               | 8088 | shiro权限管理              |
| rbac          | suphowe-rbac-token               | 8089 | token验证管理              |
| report        | suphowe-report-ureport2          | 8090 | 报表引擎                   |
| task          | suphowe-task-quartz              | 8091 | 动态quartz配置             |
| task          | suphowe-task-simple              | 8092 | 线程池执行定时任务              |
| task          | suphowe-task-xxl-job-admin       | 8093 | xxl-job-admin 定时任务调度中心 |
| task          | suphowe-task-xxl-job-core        |      | 公共依赖                   |
| task          | suphowe-task-xxl-job-sample      | 8094 | 执行器sample              |
| web           | suphowe-web-http                 | 8095 | web 公共数据处理             |
| web           | suphowe-websocket                | 8096 | websocket 公共数据处理             |





