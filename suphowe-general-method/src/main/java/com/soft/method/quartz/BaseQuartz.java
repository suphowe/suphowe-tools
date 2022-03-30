package com.soft.method.quartz;

import com.soft.method.service.AopService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务配置测试,需要添加@EnableScheduling 开启定时任务,也可以将注解添加在启动类中
 * @author suphowe
 */
@Component
@EnableScheduling
@PropertySource(value = "classpath:properties/crontab-quartz.properties")
public class BaseQuartz {

    @Resource
    AopService aopService;

    @Scheduled(cron = "${crontab.test}")
    public void task() {
        aopService.methodOne("定时任务-->");
    }
}
