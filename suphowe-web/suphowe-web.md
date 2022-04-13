# suphowe-web
springboot web服务 公共处理工具

| 说明          | 链接文件                                                                       |
|:------------|:---------------------------------------------------------------------------|
| Request请求去重 | [RequestDedupUtil.java](/src/main/java/com/soft/web/request/RequestDedupUtil.java) |



## RequestDedup 请求去重
- 传入请求 Json String 和 需要去除验证的摘要字段
- 返回 MD5 请求信息,去除摘要后,其他请求数据如果相同,则每次返回 MD5 数据相同
- 配合 Redis 或 缓存来判断请求数据是否重复
- 请求数据如果重复,从缓存中直接获取该数据进行返回
