# Redis队列使用
```$xslt
生产者  
RedisUtil.lpush(key, value)  
--------------------------------------
消费者  
RedisUtil.brpop(key, timeout, timeUnit) 
``` 

# Redis使用缓存查询
```$xslt
1.首先需要在执行正常的业务逻辑之前（查询数据库之前），查询缓存，如果缓存中没有需要的数据，查询数据库.为了防止添加缓存出错，影响正常业务代码的执行，将添加缓存的代码放置到try-catch代码快中，让程序自动捕获。
2.完成数据库的查询操作，查询完成之后需要将查询的数据添加到缓存中
3.缓存同步：数据库的增删改操作完成之后，清除对应的缓存即可，下一次执行查询操作时，重新添加新的缓存，这样就很好的实现了缓存同步的问题
```