package com.soft.quartz.jobs.impl;

import cn.hutool.core.date.DateUtil;
import com.soft.quartz.jobs.QuartzBaseTaskJob;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 基础任务调度实现类
 * @author suphowe
 */
@Component
public class QuartzBaseTaskJobImpl implements QuartzBaseTaskJob {

    private static final Logger logger = LoggerFactory.getLogger(QuartzBaseTaskJobImpl.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("===>测试Job1 时间:{}", DateUtil.now());
    }
}
