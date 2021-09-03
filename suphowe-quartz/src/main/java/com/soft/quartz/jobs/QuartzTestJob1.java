package com.soft.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public interface QuartzTestJob1 extends Job {

    @Override
    void execute(JobExecutionContext context);
}
