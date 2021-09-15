package com.soft.quartz.jobs.impl;

import cn.hutool.core.date.DateUtil;
import com.soft.quartz.jobs.QuartzTestJob1;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 基础任务调度实现类1
 * @author suphowe
 */
@Component
public class QuartzTestJobImpl1 implements QuartzTestJob1 {

    private static final Logger logger = LoggerFactory.getLogger(QuartzTestJobImpl1.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        logger.info("===>测试Job2 时间:{}", DateUtil.now());
    }
}
