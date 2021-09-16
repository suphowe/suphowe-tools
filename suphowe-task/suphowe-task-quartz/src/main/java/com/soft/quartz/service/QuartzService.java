package com.soft.quartz.service;

import cn.hutool.core.date.DateUtil;
import com.soft.quartz.dao.QuartzDao;
import com.soft.quartz.entity.SysTask;
import com.soft.quartz.system.QuartzJobManager;
import com.soft.quartz.utils.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 动态定时任务服务实现
 * @author suphowe
 */
@Service
public class QuartzService {

    private static final Logger logger = LoggerFactory.getLogger(QuartzService.class);

    @Autowired
    private QuartzDao quartzDao;

    public int addJob(String jobName, String description, String cronExpression, String beanClassName,
                      String jobGroup, String createUser) {

        logger.info("添加新定时任务,任务名称:{},任务描述:{},表达式:{},任务执行调用类:{},任务分组:{},创建者:{}",
                jobName, description, cronExpression, beanClassName, jobGroup, createUser);

        if (jobName.isEmpty() || description.isEmpty() || cronExpression.isEmpty() ||
                beanClassName.isEmpty() || jobGroup.isEmpty() || createUser.isEmpty()){
            return 412;
        }

        SysTask sysTask = new SysTask();
        sysTask.setJobName(jobName);
        sysTask.setDescription(description);
        sysTask.setCronExpression(cronExpression);
        sysTask.setBeanClass(beanClassName);
        //默认直接启用
        sysTask.setJobStatus("1");
        sysTask.setJobGroup(jobGroup);
        sysTask.setCreateUser(createUser);

        int count = quartzDao.selectCountByJobName(sysTask);
        if(count > 0) {
            return 413;
        }

        try {
            Class<?> addClass = Class.forName(beanClassName);
            QuartzJobManager.getInstance().addJob(addClass, jobName, jobGroup, cronExpression);
        } catch (Exception e) {
            logger.error("添加定时任务:{} 失败,", jobName, e);
            return 500;
        }

        int insertNum = quartzDao.insertSysTask(sysTask);
        if (insertNum != 1) {
            logger.info("添加定时任务:{} 成功,数据库操作失败", jobName);
            return 500;
        }
        logger.info("添加定时任务:{} 成功", jobName);
        return 200;
    }

    public int pauseJob(String jobName, String jobGroup, String updateUser){
        logger.info("暂停定时任务,任务名称:{},任务分组:{},更新人员:{}", jobName, jobGroup, updateUser);

        if (jobName.isEmpty() || jobGroup.isEmpty()|| updateUser.isEmpty()) {
            return 412;
        }

        try {
            QuartzJobManager.getInstance().pauseJob(jobName, jobGroup);
        } catch (Exception e) {
            logger.error("暂停定时任务:{} 失败,", jobName, e);
            return 500;
        }
        SysTask sysTask = new SysTask();
        sysTask.setJobName(jobName);
        sysTask.setJobStatus("0");
        sysTask.setJobGroup(jobGroup);
        sysTask.setUpdateUser(updateUser);
        sysTask.setUpdateTime(DateUtil.now());
        int result = quartzDao.updateSysTask(sysTask);
        if (result != 1) {
            logger.info("暂停定时任务:{} 成功,数据库操作失败", jobName);
            return 500;
        }
        return 200;
    }

    public int resumeJob(String jobName, String jobGroup, String updateUser){
        logger.info("恢复定时任务,任务名称:{},任务分组:{},更新人员:{}", jobName, jobGroup, updateUser);

        if (jobName.isEmpty() || jobGroup.isEmpty()|| updateUser.isEmpty()){
            return 412;
        }

        try {
            QuartzJobManager.getInstance().resumeJob(jobName, jobGroup);
        } catch (Exception e) {
            logger.error("恢复定时任务:{} 失败,", jobName, e);
            return 500;
        }
        SysTask sysTask = new SysTask();
        sysTask.setJobName(jobName);
        sysTask.setJobStatus("1");
        sysTask.setJobGroup(jobGroup);
        sysTask.setUpdateUser(updateUser);
        sysTask.setUpdateTime(DateUtil.now());
        int result = quartzDao.updateSysTask(sysTask);
        if (result != 1) {
            logger.info("恢复定时任务:{} 成功,数据库操作失败", jobName);
            return 500;
        }
        return 200;
    }

    public int updateJob(String jobName, String description, String jobGroup, String cronExpression, String updateUser) {
        logger.info("更新定时任务,任务名称:{},任务描述:{},表达式:{},任务分组:{},更新人员:{}",
                jobName, description, cronExpression, jobGroup, updateUser);

        if (jobName.isEmpty() || description.isEmpty() || cronExpression.isEmpty() ||
                jobGroup.isEmpty() || updateUser.isEmpty()){
            return 412;
        }
        SysTask sysTask = new SysTask();
        sysTask.setJobName(jobName);
        sysTask.setDescription(description);
        sysTask.setJobGroup(jobGroup);
        sysTask.setCronExpression(cronExpression);
        sysTask.setUpdateUser(updateUser);
        sysTask.setUpdateTime(DateUtil.now());

        //只更新描述信息时不用重启任务
        List<SysTask> oldUpdateDataList = quartzDao.selectSysTask(sysTask);
        if (oldUpdateDataList.isEmpty()){
            return 414;
        }

        SysTask oldUpdateData = oldUpdateDataList.get(0);
        if (oldUpdateData.getJobName().equals(jobName) && oldUpdateData.getJobGroup().equals(jobGroup)
                && oldUpdateData.getCronExpression().equals(cronExpression)){
            int updateDescriptionResult = quartzDao.updateSysTask(sysTask);
            if (updateDescriptionResult != 1) {
                logger.info("更新定时任务:{} 失败,数据库操作失败", jobName);
                return 500;
            }
            return 200;
        }

        try {
            QuartzJobManager.getInstance().updateJob(jobName, jobGroup, cronExpression);
        } catch (Exception e) {
            logger.error("更新定时任务:{} 失败,", jobName, e);
            return 500;
        }
        int updateResult = quartzDao.updateSysTask(sysTask);
        if (updateResult != 1) {
            logger.info("更新定时任务:{} 成功,数据库操作失败", jobName);
            return 500;
        }
        return 200;
    }

    public int deleteJob(String jobName, String jobGroup) {
        logger.info("删除定时任务,任务名称:{},任务分组:{}", jobName, jobGroup);

        if (jobName.isEmpty() || jobGroup.isEmpty()){
            return 412;
        }
        SysTask sysTask = new SysTask();
        sysTask.setJobName(jobName);
        sysTask.setJobGroup(jobGroup);

        try {
            QuartzJobManager.getInstance().deleteJob(jobName, jobGroup);
        } catch (Exception e) {
            logger.error("删除定时任务:{} 失败,", jobName, e);
            return 500;
        }

        int deleteResult = quartzDao.deleteSysTaskByJobName(sysTask);
        if (deleteResult != 1) {
            logger.info("删除定时任务:{} 成功,数据库操作失败", jobName);
            return 500;
        }
        return 200;
    }

    public int startAllPauseJobs(String updateUser) {
        logger.info("恢复所有暂停的定时任务,更新人员:{}", updateUser);

        SysTask sysTask = new SysTask();
        sysTask.setJobStatus("1");
        sysTask.setUpdateUser(updateUser);
        sysTask.setUpdateTime(DateUtil.now());
        try {
            QuartzJobManager.getInstance().startAllPauseJobs();
        } catch (Exception e) {
            logger.error("恢复所有暂停的定时任务失败,", e);
            return 500;
        }
        int startResult = quartzDao.updateAllSysTaskStatus(sysTask);
        if (startResult == 0) {
            logger.info("恢复所有暂停的定时任务成功,数据库操作失败");
            return 500;
        }
        return 200;
    }

    public int pauseAllJobs(String updateUser) {
        logger.info("暂停所有定时任务,更新人员:{}", updateUser);

        SysTask sysTask = new SysTask();
        sysTask.setJobStatus("0");
        sysTask.setUpdateUser(updateUser);
        sysTask.setUpdateTime(DateUtil.now());
        try {
            QuartzJobManager.getInstance().pauseAllJobs();
        } catch (Exception e) {
            logger.error("暂停所有定时任务失败,", e);
            return 500;
        }
        int startResult = quartzDao.updateAllSysTaskStatus(sysTask);
        if (startResult == 0) {
            logger.info("暂停所有定时任务成功,数据库操作失败");
            return 500;
        }
        return 200;
    }

    public String getAllJobsFromTable() {
        try {
            List<SysTask> allJobs =  quartzDao.selectSysTask(null);
            return HttpResult.backOut(200, HttpResult.QUARTZ_CODE_MSG, allJobs);
        } catch (Exception e) {
            logger.error("获取所有定时任务失败,", e);
            return HttpResult.backOut(500, HttpResult.QUARTZ_CODE_MSG);
        }
    }

    public String getAllJobsFromServer() {
        try {
            List<HashMap<String, Object>> allJobs = QuartzJobManager.getInstance().getAllJobs();
            return HttpResult.backOut(200, HttpResult.QUARTZ_CODE_MSG, allJobs);
        } catch (Exception e) {
            logger.error("获取所有定时任务失败,", e);
            return HttpResult.backOut(500, HttpResult.QUARTZ_CODE_MSG);
        }
    }

    public String restartAllJobsWhenSeverRestart() {
        logger.info("程序启动,重新加载启用的定时任务");
        SysTask sysTaskQuery = new SysTask();
        sysTaskQuery.setJobStatus("1");
        //获取所有启用的定时任务
        List<SysTask> usedSysTask = quartzDao.selectSysTask(null);
        List<String> failedJobs = new ArrayList<>();
        for (SysTask sysTask : usedSysTask) {
            try {
                Class<?> addClass = Class.forName(sysTask.getBeanClass());
                QuartzJobManager.getInstance().addJob(addClass, sysTask.getJobName(), sysTask.getJobGroup(), sysTask.getCronExpression());
                //定时任务未启用,加载后暂停
                if ("0".equals(sysTask.getJobStatus())){
                    QuartzJobManager.getInstance().pauseJob(sysTask.getJobName(), sysTask.getJobGroup());
                }
            } catch (Exception e) {
                failedJobs.add(sysTask.getJobName());
                logger.error("添加定时任务:{} 失败,", sysTask.getJobName(), e);
            }
        }
        return HttpResult.backOut(200, HttpResult.QUARTZ_CODE_MSG, failedJobs);
    }


}
