package com.hadoop.mapreduce.controller;

import com.hadoop.mapreduce.service.MapReduceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * MapReduce处理控制层
 * @author suphowe
 */
@RestController
@RequestMapping("/hadoop/reduce")
public class MapReduceController {

    @Autowired
    MapReduceService mapReduceService;

    /**
     * 单词统计(统计指定key单词的出现次数)
     * @param jobName job名称
     * @param inputPath 输入地址
     */
    @RequestMapping(value = "wordCount", method = RequestMethod.POST)
    @ResponseBody
    public String wordCount(@RequestParam("jobName") String jobName, @RequestParam("inputPath") String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return "请求参数为空";
        }
        mapReduceService.wordCount(jobName, inputPath);
        return "单词统计成功";
    }

    /**
     * 一年最高气温统计
     * @param jobName job名称
     * @param inputPath 输入地址
     */
    @RequestMapping(value = "MaxWeather", method = RequestMethod.POST)
    @ResponseBody
    public String weather(@RequestParam("jobName") String jobName, @RequestParam("inputPath") String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return "请求参数为空";
        }
        mapReduceService.weather(jobName, inputPath);
        return "温度统计成功";
    }

    /**
     * 获取共同好友
     * @param jobName job名称
     * @param inputPath 输入地址
     */
    @RequestMapping(value = "friends", method = RequestMethod.POST)
    @ResponseBody
    public String friends(@RequestParam("jobName") String jobName, @RequestParam("inputPath") String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return "请求参数为空";
        }
        mapReduceService.friends(jobName, inputPath);
        return "温度统计成功";
    }

    /**
     * mapreduce 表join操作
     * @param jobName job名称
     * @param inputPath 输入地址
     */
    @RequestMapping(value = "join",method= RequestMethod.POST)
    @ResponseBody
    public String join(@RequestParam("jobName") String jobName, @RequestParam("inputPath") String inputPath) throws  Exception{
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return "请求参数为空";
        }
        mapReduceService.join(jobName, inputPath);
        return "温度统计成功";
    }

    /**
     * 分组统计、排序
     * @param jobName job名称
     * @param inputPath 输入地址
     */
    @RequestMapping(value = "groupSort", method = RequestMethod.POST)
    @ResponseBody
    public String groupSort(@RequestParam("jobName") String jobName, @RequestParam("inputPath") String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return "请求参数为空";
        }
        mapReduceService.groupSort(jobName, inputPath);
        return "温度统计成功";
    }

    /**
     * 明星微博统计
     * @param jobName job名称
     * @param inputPath 输入地址
     */
    @RequestMapping(value = "weibo", method = RequestMethod.POST)
    @ResponseBody
    public String weibo(@RequestParam("jobName") String jobName, @RequestParam("inputPath") String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return "请求参数为空";
        }
        mapReduceService.weibo(jobName, inputPath);
        return "温度统计成功";
    }
}
