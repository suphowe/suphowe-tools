package com.hadoop.mapreduce.service;

import com.hadoop.mapreduce.config.HdfsConstants;
import com.hadoop.mapreduce.utils.HdfsUtils;
import com.hadoop.mapreduce.utils.ReduceJobsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.springframework.stereotype.Service;

/**
 * 单词统计
 * @author suphowe
 */
@Service
public class MapReduceService {

    /**
     * 单词统计，统计某个单词出现的次数
     * @param jobName job名称
     * @param inputPath 输入路径
     */
    public void wordCount(String jobName, String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job,如果输出路径存在则删除，保证每次都是最新的
        String outputPath = HdfsConstants.HDFS_OUTPUT_PATH + "/" + jobName;
        if (HdfsUtils.existFile(outputPath)) {
            HdfsUtils.deleteFile(outputPath);
        }
        ReduceJobsUtils.getWordCountJobsConf(jobName, inputPath, outputPath);
    }

    /**
     * 一年最高气温统计
     * @param jobName job名称
     * @param inputPath 输入路径
     */
    public void weather(String jobName, String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job
        String outputPath = HdfsConstants.HDFS_OUTPUT_PATH + "/" + jobName;
        if (HdfsUtils.existFile(outputPath)) {
            HdfsUtils.deleteFile(outputPath);
        }
        JobConf jobConf = ReduceJobsUtils.getWeatherJobsConf(jobName);
        FileInputFormat.setInputPaths(jobConf, new Path(inputPath));
        FileOutputFormat.setOutputPath(jobConf, new Path(outputPath));
        JobClient.runJob(jobConf);
    }

    /**
     * 获取共同好友
     * @param jobName job名称
     * @param inputPath 输入路径
     */
    public void friends(String jobName, String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job
        String outputPath = HdfsConstants.HDFS_OUTPUT_PATH + "/" + jobName;
        if (HdfsUtils.existFile(outputPath)) {
            HdfsUtils.deleteFile(outputPath);
        }
        ReduceJobsUtils.friends(jobName, inputPath, outputPath);
    }

    /**
     * mapreduce 表join操作
     * @param jobName job名称
     * @param inputPath 输入路径
     */
    public void join(String jobName, String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job
        String outputPath = HdfsConstants.HDFS_OUTPUT_PATH + "/" + jobName;
        if (HdfsUtils.existFile(outputPath)) {
            HdfsUtils.deleteFile(outputPath);
        }
        ReduceJobsUtils.join(jobName, inputPath, outputPath);
    }

    /**
     * mapreduce 分组统计、排序
     * @param jobName job名称
     * @param inputPath 输入路径
     */
    public void groupSort(String jobName, String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job
        String outputPath = HdfsConstants.HDFS_OUTPUT_PATH + "/" + jobName;
        if (HdfsUtils.existFile(outputPath)) {
            HdfsUtils.deleteFile(outputPath);
        }
        ReduceJobsUtils.groupSort(jobName, inputPath, outputPath);
    }

    /**
     * 明星微博统计
     * @param jobName job名称
     * @param inputPath 输入路径
     */
    public void weibo(String jobName, String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        // 输出目录 = output/当前Job
        String outputPath = HdfsConstants.HDFS_OUTPUT_PATH + "/" + jobName;
        if (HdfsUtils.existFile(outputPath)) {
            HdfsUtils.deleteFile(outputPath);
        }
        ReduceJobsUtils.weibo(jobName, inputPath, outputPath);
    }
}
