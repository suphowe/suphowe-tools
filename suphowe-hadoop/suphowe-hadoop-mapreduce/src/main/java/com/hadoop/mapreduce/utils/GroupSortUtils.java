package com.hadoop.mapreduce.utils;

import com.hadoop.mapreduce.mapper.GroupSortMapper;
import com.hadoop.mapreduce.model.GroupSort;
import com.hadoop.mapreduce.reducer.GroupSortReduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

/**
 * 分组统计，并对value排序
 * @author suphowe
 */
public class GroupSortUtils extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        // 读取配置文件
        Configuration conf = new Configuration();

        // 如果目标文件存在则删除
        Path outPath = new Path(args[1]);
        FileSystem fs = outPath.getFileSystem(conf);
        if (fs.exists(outPath)) {
            boolean flag = fs.delete(outPath, true);
        }

        // 新建一个Job
        Job job = Job.getInstance(conf, "groupSort");
        // 设置jar信息
        job.setJarByClass(GroupSortUtils.class);

        // 设置mapper信息
        job.setMapperClass(GroupSortMapper.class);
        // 设置reduce信息
        job.setReducerClass(GroupSortReduce.class);

        // 设置mapper和reduce的输出格式，如果相同则只需设置一个
        job.setOutputKeyClass(GroupSort.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置fs文件地址
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 运行
        return job.waitForCompletion(true) ? 0 : 1;
    }

//    public static void main(String[] args) throws Exception {
//        String[] filePath = { "hdfs://127.0.0.1:9000/java/groupSort.txt", "hdfs://127.0.0.1:9000/output/groupSort" };
//        int ec = ToolRunner.run(new Configuration(), new SearchStar(), filePath);
//        System.exit(ec);
//    }
}
