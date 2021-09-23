package com.hadoop.mapreduce.mapper;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * 读取一年中某天的最高气温
 * @author suphowe
 */
public class WeatherMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, LongWritable> {

    /**
     *
     * @param key 表示我们当前读取一个文件[qqq.txt] 读到多少个字节了 数量词
     * @param value 表示我们当前读的是文件的多少行 逐行读取 表示我们读取的一行文字
     * @param output 输出数据
     * @param reporter 基础参数
     */
    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, LongWritable> output, Reporter reporter)
            throws IOException {
        // 打印输入样本 如 2018120715
        System.out.println("==== Before Mapper: ====" + key + "," + value);
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
        // 截取年份
        String year = line.substring(0, 4);
        // 截取温度
        int temperature = Integer.parseInt(line.substring(8));
        Text word = new Text();
        word.set(year);
        output.collect(word, new LongWritable(temperature));

        // 打印输出样本
        System.out.println("==== After Mapper: ==== " + new Text(year) + "," + new LongWritable(temperature));
    }
}
