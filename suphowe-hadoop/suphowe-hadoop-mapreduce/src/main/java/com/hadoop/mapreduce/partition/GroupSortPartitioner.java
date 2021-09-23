package com.hadoop.mapreduce.partition;

import com.hadoop.mapreduce.model.GroupSort;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 分区过滤
 * @author suphowe
 */
public class GroupSortPartitioner extends Partitioner<GroupSort, IntWritable> {

    @Override
    public int getPartition(GroupSort key, IntWritable value, int numPartitions) {
        return Math.abs(key.getName() * 127) % numPartitions;
    }
}
