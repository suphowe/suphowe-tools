package com.hadoop.mapreduce.reducer;

import com.hadoop.mapreduce.model.GroupSort;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 分组统计
 * @author suphowe
 */
public class GroupSortReduce extends Reducer<GroupSort, IntWritable, Text, IntWritable> {

    private static final Text NAME = new Text();

    @Override
    protected void reduce(GroupSort key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        NAME.set(key + "");
        for (IntWritable value : values) {
            // reduce输出：20 1 1
            System.out.println("reduce输出：" + key + " " + value);
            context.write(NAME, value);
        }
    }
}
