# guava api限流
通过 AOP 结合 Guava 的 RateLimiter 实现限流，旨在保护 API 被恶意频繁访问的问题。

-test1 接口未被限流的时候
![1.png](./doc/picture/063719.jpg)
-test1 接口频繁刷新，触发限流的时候
![1.png](./doc/picture/063718-1.jpg)
-test2 接口不做限流，可以一直刷新
![1.png](./doc/picture/063718.jpg)