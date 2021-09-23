package com.hadoop.mapreduce.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计单个字符出现的次数
 * @author suphowe
 */
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    private final IntWritable result = new IntWritable();
    private final String text = "孙权";
    private int textSum = 0;
    private List<String> textList = null;

    public WordCountReduce() {
        textList = new ArrayList<>();
        textList.add("曹操");
        textList.add("孙权");
    }

    /**
     * @param key 传入的单词名称，是Mapper中传入的
     * @param values LongWritable 是该单词出现了多少次，这个是mapreduce计算出来的，比如 hello出现了11次
     * @param context 输出单词的名称 ，这里是要输出到文本中的内容
     */
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        result.set(sum);
        context.write(key, result);

        String keyStr = key.toString();
        // 未使用分词器，需要根据map传过来的行内容检索并累加
        // boolean isHas = keyStr.contains(text);
        // if (isHas) {
        // textSum++;
        // System.out.println("============ " + text + " 统计分词为: " + textSum + "
        // ============");
        // }

        // 使用分词器，内容已经被统计好了，直接输出即可
        if (textList.contains(keyStr)) {
            System.out.println("============ " + keyStr + " 统计分词为: " + sum + " ============");
        }
    }
}
