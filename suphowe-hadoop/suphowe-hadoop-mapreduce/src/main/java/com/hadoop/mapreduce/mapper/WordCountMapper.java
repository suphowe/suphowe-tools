package com.hadoop.mapreduce.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * 统计单个字符出现的次数
 * @author suphowe
 */
public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable ONE = new IntWritable(1);

    private final Text word = new Text();

    /**
     * 读取 sgyy.txt或者dpcq.txt 内容格式为小说内容
     * @param key 默认情况下，是mapreduce所读取到的一行文本的起始偏移量
     * @param value 默认情况下，是mapreduce所读取到的一行文本的内容，hadoop中的序列化类型为Text
     * @param context 是用户自定义逻辑处理完成后输出的KEY，在此处是单词，String
     */
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // 防止中文乱码
        String line = new String(value.getBytes(), 0, value.getLength(), StandardCharsets.UTF_8).trim();
        if (StringUtils.isNotEmpty(line)) {
            // 使用分词器，分隔文件行内容根据常用的短语分隔，比如我们，被分隔成 <我,1>,<们,1><我们,1>
            byte[] btValue = line.getBytes();
            InputStream inputStream = new ByteArrayInputStream(btValue);
            Reader reader = new InputStreamReader(inputStream);
            IKSegmenter ikSegmenter = new IKSegmenter(reader, true);
            Lexeme lexeme;
            while ((lexeme = ikSegmenter.next()) != null) {
                word.set(lexeme.getLexemeText());
                context.write(word, ONE);
            }
        }
    }
}
