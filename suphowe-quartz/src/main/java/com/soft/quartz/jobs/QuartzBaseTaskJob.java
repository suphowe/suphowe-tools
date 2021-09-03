package com.soft.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 基础任务调度接口
 * @author suphowe
 */
public interface QuartzBaseTaskJob extends Job {

    @Override
    void execute(JobExecutionContext context);
}
