package com.hadoop.mapreduce.mapper;

import com.hadoop.mapreduce.model.GroupSort;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 分组统计排序mapper类 读取 /java/groupSort.txt 文件，内容格式 40 20 30 20
 * @author suphowe
 */
public class GroupSortMapper extends Mapper<LongWritable, Text, GroupSort, IntWritable>  {

    private static final GroupSort GROUP_SORT = new GroupSort();

    private static final IntWritable NUM = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split("\t");
        if (split.length >= 2) {
            GROUP_SORT.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            NUM.set(Integer.parseInt(split[1]));
            // {"name":40,"num":20} 20
            // System.out.println("mapper输出：" +
            // JsonUtil.toJSON(groupSortModel) + " " + num);
            context.write(GROUP_SORT, NUM);
        }
    }
}
