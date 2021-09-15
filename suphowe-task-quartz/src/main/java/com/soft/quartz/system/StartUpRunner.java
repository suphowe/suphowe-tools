package com.soft.quartz.system;

import com.soft.quartz.service.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化执行,工程启动时执行
 * @author suphowe
 */
@Component
@Order(1)
public class StartUpRunner implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(StartUpRunner.class);

    /**
     * 定时任务初始化
     */
    @Resource
    private QuartzService quartzService;

    @Override
    public void run(String... args) {
        logger.info("===>初始化,启动完成后立即执行");
        try {
            // 服务启动重新载入定时任务
            quartzService.restartAllJobsWhenSeverRestart();
        } catch (Exception e) {
            logger.error("初始化程序失败,", e);
        }
    }
}
