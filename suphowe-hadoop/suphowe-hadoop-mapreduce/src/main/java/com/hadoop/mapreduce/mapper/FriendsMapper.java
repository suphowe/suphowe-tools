package com.hadoop.mapreduce.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 查找共同的好友
 * @author suphowe
 */
public class FriendsMapper extends Mapper<LongWritable, Text, Text, Text> {

    private final Text k = new Text();
    private final Text v = new Text();

    /**
     * 读取 friends.txt 内容格式 A:B,C,D,F,E,O
     * @param key 表示我们当前读取一个文件[qqq.txt] 读到多少个字节了 数量词
     * @param value 表示我们当前读的是文件的多少行 逐行读取 表示我们读取的一行文字
     * @param context 是输出单词的名称 ，这里是要输出到文本中的内容
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();
        // 根据冒号拆分
        String[] personFriends = line.split(":");
        // 第一个为用户
        String person = personFriends[0];
        // 第二个为好友
        String friends = personFriends[1];
        // 好友根据逗号拆分
        String[] friendsList = friends.split(",");
        for (String friend : friendsList) {
            k.set(friend);
            v.set(person);
            context.write(k, v);
        }
    }
}