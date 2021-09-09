package com.soft.method.udp.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author suphowe
 */
@Slf4j
@Service
public class BusinessService {

    @Override
    @Async("threadPoolTaskExecutor")
    public void udpHandleMethod(String message) throws Exception {
        log.info("业务开始处理");
        Thread.sleep(3000);
        log.info("业务处理完成");
    }

}
