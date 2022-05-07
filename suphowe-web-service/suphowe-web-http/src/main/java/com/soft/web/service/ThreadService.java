package com.soft.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 服务线程
 * @author suphowe
 */
@Service
public class ThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ThreadService.class);

    public void printMessage(String message) {
        logger.info("打印消息:{}", message);
    }
}
