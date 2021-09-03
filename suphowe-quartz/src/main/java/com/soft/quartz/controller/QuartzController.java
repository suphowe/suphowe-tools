package com.soft.quartz.controller;

import com.soft.quartz.service.QuartzService;
import com.soft.quartz.utils.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 配置文件操作
 * @author suphowe
 */

@CrossOrigin
@RestController
@RequestMapping("/quartz")
@Api(value = "动态管理定时任务")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    @ResponseBody
    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    @ApiOperation(value = "添加定时任务")
    public String addJob(String jobName, String description, String cronExpression, String beanClassName, String jobGroup, String createUser){
        return HttpResult.backOut(quartzService.addJob(jobName, description, cronExpression, beanClassName, jobGroup, createUser), HttpResult.QUARTZ_CODE_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    @ApiOperation(value = "暂停定时任务")
    public String pauseJob(String jobName, String jobGroup, String updateUser){
        return HttpResult.backOut(quartzService.pauseJob(jobName, jobGroup, updateUser), HttpResult.QUARTZ_CODE_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/resumeJob", method = RequestMethod.POST)
    @ApiOperation(value = "恢复定时任务")
    public String resumeJob(String jobName, String jobGroup, String updateUser){
        return HttpResult.backOut(quartzService.resumeJob(jobName, jobGroup, updateUser), HttpResult.QUARTZ_CODE_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/updateJob", method = RequestMethod.POST)
    @ApiOperation(value = "更新定时任务")
    public String updateJob(String jobName, String description, String jobGroup, String cronExpression, String updateUser){
        return HttpResult.backOut(quartzService.updateJob(jobName, description, jobGroup, cronExpression, updateUser), HttpResult.QUARTZ_CODE_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    @ApiOperation(value = "删除定时任务")
    public String deleteJob(String jobName, String jobGroup){
        return HttpResult.backOut(quartzService.deleteJob(jobName, jobGroup), HttpResult.QUARTZ_CODE_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/startAllPauseJobs", method = RequestMethod.POST)
    @ApiOperation(value = "恢复所有暂停的定时任务")
    public String startAllPauseJobs(String updateUser){
        return HttpResult.backOut(quartzService.startAllPauseJobs(updateUser), HttpResult.QUARTZ_CODE_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/pauseAllJobs", method = RequestMethod.POST)
    @ApiOperation(value = "暂停所有定时任务")
    public String pauseAllJobs(String updateUser){
        return HttpResult.backOut(quartzService.pauseAllJobs(updateUser), HttpResult.QUARTZ_CODE_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllJobsFromServer", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有定时任务")
    public String getAllJobsFromServer(){
        return quartzService.getAllJobsFromServer();
    }

    @ResponseBody
    @RequestMapping(value = "/getAllJobsFromTable", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有定时任务")
    public String getAllJobsFromTable(){
        return quartzService.getAllJobsFromTable();
    }

}
