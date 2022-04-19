# suphowe-web
springboot web服务 公共处理工具

| 说明          | 链接文件                                                                                                                                                                                                                                      |
|:------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Request请求去重 | [RequestDedupUtil.java](/src/main/java/com/soft/web/request/RequestDedupUtil.java)<br/>[RequestService.java](/src/main/java/com/soft/web/service/RequestService.java)                                                                     |
| Aop 日志记录 | [SysLog.java](/src/main/java/com/soft/web/annotate/SysLog.java)<br/>[SysLogAspect.java](/src/main/java/com/soft/web/system/SysLogAspect.java)                                                                                             |
| 自定义拦截器 | [CustomizeInterceptor.java](/src/main/java/com/soft/system/CustomizeInterceptor.java)<br/>[CustomizeWebMvcConfig.java](/src/main/java/com/soft/system/CustomizeWebMvcConfig.java)                                                         |  
| 初始化任务 | [StartCommandLineRunner.java](/src/main/java/com/soft/system/StartCommandLineRunner.java)                                                                                                                                                 |
| 接口数据加密 | [ResponseEncrypt.java](/src/main/java/com/soft/annotate/ResponseEncrypt.java)<br/>[ResponseEncryptAdvice.java](/src/main/java/com/soft/system/ResponseEncryptAdvice.java)<br/>[AesUtils.java](/src/main/java/com/soft/util/AesUtils.java) |
| 线程池使用 | [ExecutorConfig.java]()<br/>[VisiableThreadPoolTaskExecutor.java]()<br/>[AsyncService.java]()                                                                                                                                             |
| 统一返回类 | [ResponseBody.java]()<br/>[ResponseMsg.java]()                                                                                                                                                                                                |


## RequestDedup 请求去重
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>${redis.version}</version>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>${jedis.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>${commons.pool.version}</version>
</dependency>
```
- 传入请求 Json String 和 需要去除验证的摘要字段
- 返回 MD5 请求信息,去除摘要后,其他请求数据如果相同,则每次返回 MD5 数据相同
- 配合 Redis 或 缓存来判断请求数据是否重复
- 请求数据如果重复,从缓存中直接获取该数据进行返回

## AOP 日志记录
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
    <version>${aop.version}</version>
</dependency>
```

- 自定义注解表明AOP类别等信息
- 定义[@Pointcut]()，并在[@AfterReturning]() 后进行处理
- 在需要记录的 controller上添加 [@SysLog(module = "module", funcType = "type", funcDesc = "desc")]()

## 自定义拦截器
- 实现拦截器配置 [WebMvcConfigurer]()， 添加拦截规则，需要拦截的uri 和 放行的白名单uri
- 自定义拦截器实现 [HandlerInterceptor]()， 在 [preHandle]() 方法中判断校验规则

## 初始化任务
- 实现 [CommandLineRunner]() 方法
- 在 run 方法中添加初始化执行初始化
- 启动类中添加 [@EnableCaching]() 注解

## 接口数据加密
- application中写入密钥
- 配置自定义注解 [ResponseEncrypt.java]()
- 编写加密和解密类 [AesUtils.java]()
- 实现 [ResponseBodyAdvice]() ，对传入body和返回值进行操作，判断返回是否为 [ResponseMsg]()， 是则进行加密处理
- 在需要进行加密的controller ，返回为 [ResponseMsg]()， 添加注解 [@ResponseEncrypt]()

## 线程池使用
- 线程池配置 [ExecutorConfig.java]()
```properties
# 异步线程配置
# 配置核心线程数
async.executor.thread.core_pool_size = 5
# 配置最大线程数
async.executor.thread.max_pool_size = 5
# 配置队列大小
async.executor.thread.queue_capacity = 99999
# 配置线程池中的线程的名称前缀
async.executor.thread.name.prefix = async-service-
```
- 配置线程池使用情况打印 [VisiableThreadPoolTaskExecutor.java]()
- 创建线程池的使用 [AsyncService.java]()

## 统一返回类
- 返回消息实体类 [ResponseMsg.java]()
- 返回 body 处理 [ResponseBody.java]()
- 使用方法
```text
// 无data
return new ResponseBody("200").createNullDataBody();

// 存在data
return new ResponseBody("200", data).createDataBody();
```