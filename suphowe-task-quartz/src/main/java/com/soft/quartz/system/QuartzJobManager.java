package com.soft.quartz.system;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Quartz Job管理类
 * (对Job进行增加,暂停,恢复,更新,删除)
 * @author suphowe
 */
@Component
public class QuartzJobManager {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobManager.class);

    private static QuartzJobManager quartzJobManager;

    @Autowired
    private Scheduler scheduler;

    public QuartzJobManager() {
        logger.info("初始化QuartzJobManager");
        quartzJobManager = this;
    }

    /**
     * 返回QuartzJobManager的方法
     */
    public static QuartzJobManager getInstance() {
        return QuartzJobManager.quartzJobManager;
    }

    /**
     * 创建job
     *
     * @param clazz 任务类
     * @param jobName 任务名称
     * @param jobGroupName 任务所在组名称
     * @param cronExpression cron表达式
     */
    public void addJob(Class clazz, String jobName, String jobGroupName, String cronExpression) throws Exception {
        // 启动调度器
        scheduler.start();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroupName).build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }


    /**
     * 创建job，可传参
     *
     * @param clazz          任务类
     * @param jobName        任务名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     * @param argMap         map形式参数
     */
    public void addJob(Class clazz, String jobName, String jobGroupName, String cronExpression, Map<String, Object> argMap) throws Exception {
        // 启动调度器
        scheduler.start();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroupName).build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                .withSchedule(scheduleBuilder).build();
        //获得JobDataMap，写入数据
        trigger.getJobDataMap().putAll(argMap);
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     */
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 恢复job
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     */
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
    }


    /**
     * job 更新,只更新频率
     *
     * @param jobName        任务名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     */
    public void updateJob(String jobName, String jobGroupName, String cronExpression) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);

    }


    /**
     * job 更新,更新频率和参数
     *
     * @param jobName        任务名称
     * @param jobGroupName   任务所在组名称
     * @param cronExpression cron表达式
     * @param argMap         参数
     */
    public void updateJob(String jobName, String jobGroupName, String cronExpression, Map<String, Object> argMap) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        //修改map
        trigger.getJobDataMap().putAll(argMap);

        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);

    }

    /**
     * job 更新,只更新更新参数
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     * @param argMap       参数
     */
    public void updateJob(String jobName, String jobGroupName, Map<String, Object> argMap) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        //修改map
        trigger.getJobDataMap().putAll(argMap);

        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);

    }


    /**
     * job 删除
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务所在组名称
     */
    public void deleteJob(String jobName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 启动所有定时任务
     */
    public void startAllJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            logger.info("启动所有定时任务失败,", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复所有暂停的定时任务
     */
    public void startAllPauseJobs() {
        try {
            List<HashMap<String, Object>> allJobs = getAllJobs();
            for (HashMap<String, Object> job : allJobs) {
                scheduler.resumeJob(JobKey.jobKey(job.get("jobName").toString(), job.get("jobGroupName").toString()));
            }
        } catch (Exception e) {
            logger.info("恢复所有暂停的定时任务失败,", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 暂停所有定时任务
     */
    public void pauseAllJobs() {
        try {
            List<HashMap<String, Object>> allJobs = getAllJobs();
            for (HashMap<String, Object> job : allJobs) {
                scheduler.pauseJob(JobKey.jobKey(job.get("jobName").toString(), job.get("jobGroupName").toString()));
            }
        } catch (Exception e) {
            logger.info("暂停所有定时任务失败,", e);
            throw new RuntimeException(e);
        }


    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownAllJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 获取所有任务列表
     */
    public List<HashMap<String, Object>> getAllJobs() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<HashMap<String, Object>> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                LinkedHashMap<String, Object> job = new LinkedHashMap<>(8);
                job.put("jobName", jobKey.getName());
                job.put("jobGroupName", jobKey.getGroup());
                job.put("trigger", trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.put("cronExpression", cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }
}
