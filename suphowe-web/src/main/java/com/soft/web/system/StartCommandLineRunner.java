package com.soft.web.system;

import com.soft.web.service.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化执行
 * @author suphowe
 */
@Order(1)
@Component
public class StartCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartCommandLineRunner.class);

    @Resource
    ThreadService threadService;

    @Override
    public void run(String... args) throws Exception {
        threadService.printMessage("初始化任务");
    }
}
