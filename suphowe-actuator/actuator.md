# suphowe-actuator
Actuator 模块 详解：健康检查，度量，指标收集和监控

## 访问地址
```
默认访问地址：
http://localhost:8080/actuator

可通过配置文件中进行修改
management.endpoints.web.base-path=/monitor
访问路径变更为：
http://localhost:8080/monitor
```

## /health端点
```
/health端点会聚合你程序的健康指标，来检查程序的健康情况。端点公开的应用健康信息取决于：
management.endpoint.health.show-details=always
复制代码该属性可以使用以下值之一进行配置：
```
| Name | Description |
| ----- | ----- |
| never | 不展示详细信息，up或者down的状态，默认配置 |
| when-authorized | 详细信息将会展示给通过认证的用户。授权的角色可以通过management.endpoint.health.roles配置 |
| always | 对所有用户暴露详细信息 |

访问地址
```
http://localhost:8080/monitor/health
```

配置文件
```
#  "*" 代表暴露所有的端点 如果指定多个端点，用","分开
management.endpoints.web.exposure.include=*
# 排除端点,规则同上
#management.endpoints.web.exposure.exclude=*
# 监控点路径
management.endpoints.web.base-path=/monitor
# 健康指标
management.endpoints.health.show-details=always
# 禁用mongo组件的健康检测
#management.health.mongo.enabled=false
# 禁用所有自动配置的健康指示器
management.health.defaults.enabled=false
```

## /conditions 详解自动配置
接口能告诉你为什么会有这个bean,或者为什么没有这个bean

## /env 查看属性配置
应用程序可用的所有环境属性的列表

## /configprops 查看属性的使用方法
接口报告，说明属性如何进行设置(注入或其他方式)

## /mappings 接口道控制器的映射
控制器接口映射列表

## /metrics 应用程序指标值
应用程序计数器和度量器的快照

### 接口信息分类
```
/metrics/{MetricName}
```
| 分类 | 前缀 | 报告内容 |
| ----- | ----- | ----- |
| 垃圾收集器 | gc.* | 已经发生过的垃圾收集次数，以及垃圾收集所耗费的时间，适用于标记-清理垃圾收集器和并行垃圾收集器(数据源自java.lang.management. GarbageCollectorMXBean) |
| 内存 | mem.* | 分配给应用程序的内存数量和空闲的内存数量(数据源自java.lang. Runtime) |
| 堆 | heap.* | 当前内存用量(数据源自java.lang.management.MemoryUsage) |
| 类加载器 | classes.* | JVM类加载器加载与卸载的类的数量(数据源自java.lang. management.ClassLoadingMXBean) |
| 系统 | processors、instance.uptime、uptime、systemload.average | 系统信息，例如处理器数量(数据源自java.lang.Runtime)、运行时间(数据源自java.lang.management.RuntimeMXBean)、平均负载(数据源自java.lang.management.OperatingSystemMXBean) |
| 线程池 | thread.* | 线程、守护线程的数量，以及JVM启动后的线程数量峰值(数据源自 java.lang .management.ThreadMXBean) |
| 数据源 | datasource.* | 数据源连接的数量(源自数据源的元数据，仅当Spring应用程序上下文里存在 DataSource Bean 的时候才会有这个信息) |
| Tomcat 会话 | httpsessions.* | Tomcat的活跃会话数和最大会话数(数据源自嵌入式Tomcat的Bean，仅在使用嵌入式Tomcat服务器运行应用程序时才有这个信息) |
| HTTP | counter.status.、gauge.response. | 多种应用程序服务HTTP请求的度量值与计数器 |

| 序号 | 参数                           | 参数说明                                 | 是否监控 | 监控手段                                              | 重要度 |
| ------ | -------------------------------- | -------------------------------------------- | -------- | --------------------------------------------------------- | ------ |
| JVM    | JVM                              | JVM                                          | JVM      | JVM                                                       | JVM    |
| 1      | jvm.memory.max                   | JVM 最大内存                             |          |                                                           |        |
| 2      | jvm.memory.committed             | JVM 可用内存                             | 是      | 展示并监控堆内存和 Metaspace                     | 重要 |
| 3      | jvm.memory.used                  | JVM 已用内存                             | 是      | 展示并监控堆内存和 Metaspace                     | 重要 |
| 4      | jvm.buffer.memory.used           | JVM 缓冲区已用内存                    |          |                                                           |        |
| 5      | jvm.buffer.count                 | 当前缓冲区数                           |          |                                                           |        |
| 6      | jvm.threads.daemon               | JVM 守护线程数                          | 是      | 显示在监控页面                                     |        |
| 7      | jvm.threads.live                 | JVM 当前活跃线程数                    | 是      | 显示在监控页面；监控达到阈值时报警       | 重要 |
| 8      | jvm.threads.peak                 | JVM 峰值线程数                          | 是      | 显示在监控页面                                     |        |
| 9      | jvm.classes.loaded               | 加载 classes 数                           |          |                                                           |        |
| 10     | jvm.classes.unloaded             | 未加载的 classes 数                     |          |                                                           |        |
| 11     | jvm.gc.memory.allocated          | GC 时，年轻代分配的内存空间      |          |                                                           |        |
| 12     | jvm.gc.memory.promoted           | GC 时，老年代分配的内存空间      |          |                                                           |        |
| 13     | jvm.gc.max.data.size             | GC 时，老年代的最大内存空间      |          |                                                           |        |
| 14     | jvm.gc.live.data.size            | FullGC 时，老年代的内存空间        |          |                                                           |        |
| 15     | jvm.gc.pause                     | GC 耗时                                    | 是      | 显示在监控页面                                     |        |

| TOMCAT |   |   |   |   |   |
| ------ | -------------------------------- | -------------------------------------------- | -------- | --------------------------------------------------------- | ------ |
| 序号 | 参数                           | 参数说明                                 | 是否监控 | 监控手段                                              | 重要度 |
| 16     | tomcat.sessions.created          | tomcat 已创建 session 数                 |          |                                                           |        |
| 17     | tomcat.sessions.expired          | tomcat 已过期 session 数                 |          |                                                           |        |
| 18     | tomcat.sessions.active.current   | tomcat 活跃 session 数                    |          |                                                           |        |
| 19     | tomcat.sessions.active.max       | tomcat 最多活跃 session 数              | 是      | 显示在监控页面，超过阈值可报警或者进行动态扩容 | 重要 |
| 20     | tomcat.sessions.alive.max.second | tomcat 最多活跃 session 数持续时间  |          |                                                           |        |
| 21     | tomcat.sessions.rejected         | 超过 session 最大配置后，拒绝的 session 个数 | 是      | 显示在监控页面，方便分析问题                |        |
| 22     | tomcat.global.error              | 错误总数                                 | 是      | 显示在监控页面，方便分析问题                |        |
| 23     | tomcat.global.sent               | 发送的字节数                           |          |                                                           |        |
| 24     | tomcat.global.request.max        | request 最长时间                         |          |                                                           |        |
| 25     | tomcat.global.request            | 全局 request 次数和时间               |          |                                                           |        |
| 26     | tomcat.global.received           | 全局 received 次数和时间              |          |                                                           |        |
| 27     | tomcat.servlet.request           | servlet 的请求次数和时间             |          |                                                           |        |
| 28     | tomcat.servlet.error             | servlet 发生错误总数                   |          |                                                           |        |
| 29     | tomcat.servlet.request.max       | servlet 请求最长时间                   |          |                                                           |        |
| 30     | tomcat.threads.busy              | tomcat 繁忙线程                          | 是      | 显示在监控页面，据此检查是否有线程夯住 |        |
| 31     | tomcat.threads.current           | tomcat 当前线程数（包括守护线程） | 是      | 显示在监控页面                                     | 重要 |
| 32     | tomcat.threads.config.max        | tomcat 配置的线程最大数              | 是      | 显示在监控页面                                     | 重要 |
| 33     | tomcat.cache.access              | tomcat 读取缓存次数                    |          |                                                           |        |
| 34     | tomcat.cache.hit                 | tomcat 缓存命中次数                    |          |                                                           |        |

| CPU |   |   |   |   |   |
| ------ | -------------------------------- | -------------------------------------------- | -------- | --------------------------------------------------------- | ------ |
| 序号 | 参数                           | 参数说明                                 | 是否监控 | 监控手段                                              | 重要度 |
| 35     | system.cpu.count                 | CPU 数量                                   |  |  |  |
| 36     | system.load.average.1m           | load average                                 | 是      | 超过阈值报警                                        | 重要 |
| 37     | system.cpu.usage                 | 系统 CPU 使用率                         |          |                                                           |        |
| 38     | process.cpu.usage                | 当前进程 CPU 使用率                   | 是      | 超过阈值报警                                        |        |
| 39     | http.server.requests             | http 请求调用情况                      | 是      | 显示 10 个请求量最大，耗时最长的 URL；统计非 200 的请求量 | 重要 |
| 40     | process.uptime                   | 应用已运行时间                        | 是      | 显示在监控页面                                     |        |
| 41     | process.files.max                | 允许最大句柄数                        | 是      | 配合当前打开句柄数使用                         |        |
| 42     | process.start.time               | 应用启动时间点                        | 是      | 显示在监控页面                                     |        |
| 43     | process.files.open               | 当前打开句柄数                        | 是      | 监控文件句柄使用率，超过阈值后报警       | 重要 |

## /httptrace 追踪 Web 请求
在springboot 2.x 中，需要实现HttpTraceRepository

### TraceConfig.class
```
/**
 * actuator web请求跟踪
 * @author suphowe
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(HttpTraceProperties.class)
@AutoConfigureBefore(HttpTraceAutoConfiguration.class)
public class TraceConfig {

    private List<HttpTrace> list = new CopyOnWriteArrayList<>();

    @Bean
    @ConditionalOnMissingBean(HttpTraceRepository.class)
    public HttpTraceRepository httpTraceProperties() {
        return new HttpTraceRepository() {
            @Override
            public List<HttpTrace> findAll() {
                return list;
            }

            @Override
            public void add(HttpTrace trace) {
                if (list.size() > 99) {
                    list.remove(0);
                }
            }
        };
    }
}
```

## /heapdump 导出线程活动
/heapdump 接口会生成当前线程活动的快照

## /shutdown 关闭应用程序
要求management.endpoint.shutdown.enabled设置为true
```
#禁用密码验证
management.endpoint.shutdown.sensitive=false
# 启用shutdown
management.endpoint.shutdown.enabled=true
```
访问地址
```
http://localhost:8080/monitor/shutdown
```