package com.ratelimit.guava.controller;

import cn.hutool.core.lang.Dict;
import com.ratelimit.guava.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * guava limiter api限流调试
 * @author suphowe
 */
@Slf4j
@RestController
public class GuavaRateLimiterController {

    @RateLimiter(value = 1.0, timeout = 3000)
    @GetMapping("/test1")
    public Dict test1() {
        log.info("【test1】被执行了。。。。。");
        return Dict.create().set("msg", "hello,world!").set("description", "api 访问限流");
    }

    @GetMapping("/test2")
    public Dict test2() {
        log.info("【test2】被执行了。。。。。");
        return Dict.create().set("msg", "hello,world!").set("description", "api 未被访问限流");
    }

}
