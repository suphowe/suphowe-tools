package com.soft.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步线程接口
 * @author suphowe
 */
@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    /**
     * '@Async("asyncServiceExecutor")'为线程配置类中的bean
     */
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        logger.info("start executeAsync");


        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");

        logger.info("end executeAsync");
    }
}
