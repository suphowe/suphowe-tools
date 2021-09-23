package com.hadoop.mapreduce.utils;

import com.hadoop.mapreduce.compare.GroupSortComparator;
import com.hadoop.mapreduce.config.HdfsConstants;
import com.hadoop.mapreduce.mapper.*;
import com.hadoop.mapreduce.model.Order;
import com.hadoop.mapreduce.model.Weibo;
import com.hadoop.mapreduce.partition.GroupSortPartitioner;
import com.hadoop.mapreduce.reducer.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Map/Reduce工具类
 * @author suphowe
 */
@Component
public class ReduceJobsUtils {

    /**
     * 获取HDFS配置信息
     */
    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", HdfsConstants.HDFS_PATH);
        configuration.set("mapred.job.tracker", HdfsConstants.HDFS_PATH);
        // 运行在yarn的集群模式
        // configuration.set("mapreduce.framework.name", "yarn");
        // 这个配置是让main方法寻找该机器的mr环境
        // configuration.set("yarn.resourcemanmager.hostname", "node1");
        return configuration;
    }

    /**
     * 获取单词统计的配置信息
     *
     * @param jobName job名称
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void getWordCountJobsConf(String jobName, String inputPath, String outputPath)
            throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = getConfiguration();
        Job job = Job.getInstance(conf, jobName);

        job.setMapperClass(WordCountMapper.class);
        job.setCombinerClass(WordCountReduce.class);
        job.setReducerClass(WordCountReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 小文件合并设置
        job.setInputFormatClass(CombineTextInputFormat.class);
        // 最大分片
        CombineTextInputFormat.setMaxInputSplitSize(job, 4 * 1024 * 1024);
        // 最小分片
        CombineTextInputFormat.setMinInputSplitSize(job, 2 * 1024 * 1024);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));
        job.waitForCompletion(true);
    }

    /**
     * 获取单词一年最高气温计算配置
     * @param jobName job名称
     */
    public static JobConf getWeatherJobsConf(String jobName) {
        JobConf jobConf = new JobConf(getConfiguration());
        jobConf.setJobName(jobName);
        jobConf.setOutputKeyClass(Text.class);
        jobConf.setOutputValueClass(LongWritable.class);
        jobConf.setMapperClass(WeatherMapper.class);
        jobConf.setReducerClass(WeatherReduce.class);
        jobConf.setInputFormat(TextInputFormat.class);
        jobConf.setOutputFormat(TextOutputFormat.class);
        return jobConf;
    }

    /**
     * 获取共同好友
     * @param jobName job名称
     * @param inputPath 输入文件路径
     * @param outputPath 输出文件路径
     */
    public static void friends(String jobName, String inputPath, String outputPath)
            throws IOException, ClassNotFoundException, InterruptedException {
        Configuration config = getConfiguration();
        Job job = Job.getInstance(config, jobName);
        // 设置jar中的启动类，可以根据这个类找到相应的jar包
        job.setJarByClass(FriendsMapper.class);

        job.setMapperClass(FriendsMapper.class);
        job.setReducerClass(FriendsReduce.class);

        // 一般情况下mapper和reducer的输出的数据类型是一样的，所以我们用上面两条命令就行，如果不一样，我们就可以用下面两条命令单独指定mapper的输出key、value的数据类型
        // 设置Mapper的输出
        // job.setMapOutputKeyClass(Text.class);
        // job.setMapOutputValueClass(Text.class);

        // 设置reduce的输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // 指定输入输出文件的位置
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);
    }

    /**
     * mapreduce 表join
     * @param jobName job名称
     * @param inputPath 输入文件路径
     * @param outputPath 输出文件路径
     */
    public static void join(String jobName, String inputPath, String outputPath)
            throws IOException, ClassNotFoundException, InterruptedException {
        Configuration config = getConfiguration();
        Job job = Job.getInstance(config, jobName);
        // 设置jar中的启动类，可以根据这个类找到相应的jar包
        job.setJarByClass(Order.class);

        job.setMapperClass(JoinMapper.class);
        job.setReducerClass(JoinReduce.class);

        // 设置Mapper的输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Order.class);

        // 设置reduce的输出
        job.setOutputKeyClass(Order.class);
        job.setOutputValueClass(NullWritable.class);

        // 指定输入输出文件的位置
        FileInputFormat.setInputPaths(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);
    }

    /**
     * 分组统计、排序
     * @param jobName job名称
     * @param inputPath 输入文件路径
     * @param outputPath 输出文件路径
     */
    public static void groupSort(String jobName, String inputPath, String outputPath)
            throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = getConfiguration();
        Job job = Job.getInstance(conf, jobName);
        job.setJarByClass(GroupSortUtils.class);

        // 设置reduce文件拆分个数
        // job.setNumReduceTasks(3);
        // 设置mapper信息
        job.setMapperClass(GroupSortMapper.class);
        job.setPartitionerClass(GroupSortPartitioner.class);
        job.setGroupingComparatorClass(GroupSortComparator.class);
        // 设置reduce信息
        job.setReducerClass(GroupSortReduce.class);

        // 设置Mapper的输出
        job.setMapOutputKeyClass(GroupSortUtils.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置mapper和reduce的输出格式，如果相同则只需设置一个
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 指定输入文件的位置
        FileInputFormat.addInputPath(job, new Path(inputPath));
        // 指定输入文件的位置
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        // 运行
        job.waitForCompletion(true);
    }

    /**
     * 明星微博统计
     * @param jobName job名称
     * @param inputPath 输入文件路径
     * @param outputPath 输出文件路径
     */
    public static void weibo(String jobName, String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = getConfiguration();
        Job job = Job.getInstance(conf, jobName);
        job.setJarByClass(Weibo.class);

        // 指定Mapper的类
        job.setMapperClass(WeiboMapper.class);
        // 指定reduce的类
        job.setReducerClass(WeiboReduce.class);

        // 设置Mapper的输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // 设置Mapper输出的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 指定输入文件的位置
        FileInputFormat.addInputPath(job, new Path(inputPath));
        // 指定输入文件的位置
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        // 自定义输入输出格式
        // job.setInputFormatClass(WeiboInputFormat.class);
        // MultipleOutputs.addNamedOutput(job, "friends", org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class, Text.class, IntWritable.class);
        // MultipleOutputs.addNamedOutput(job, "followers", org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class, Text.class, IntWritable.class);
        // MultipleOutputs.addNamedOutput(job, "num", org.apache.hadoop.mapreduce.lib.output.TextOutputFormat.class, Text.class, IntWritable.class);

        // 将job中的参数，提交到yarn中运行
        job.waitForCompletion(true);
    }

}
