package com.hadoop.mapreduce.reducer;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 获取共同好友
 * @author suphowe
 */
public class FriendsReduce extends Reducer<Text, Text, Text, Text> {

    private final Text k = new Text();
    private final Text v = new Text();

    /**
     * 读取 FriendsMapper1 输出，内容格式 B A
     * @param key 传入的单词名称，是Mapper中传入的
     * @param values LongWritable 是该单词出现了多少次，这个是mapreduce计算出来的，比如 hello出现了11次
     * @param context 输出单词的名称 ，这里是要输出到文本中的内容
     */
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuffer sb = new StringBuffer();
        // 循环好友
        for (Text person : values) {
            sb.append(person).append(",");
        }
        k.set(key);
        v.set(sb.toString());
        context.write(k, v);
    }
}
