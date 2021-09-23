package com.hadoop.mapreduce.mapper;

import com.hadoop.mapreduce.model.Weibo;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 统计明星微博的粉丝，关注，微博数
 * @author suphowe
 */
public class WeiboMapper extends Mapper<Text, Weibo, Text, Text> {

    /**
     * 读取 /java/weibo/weibo.txt 文件，内容格式如下 唐嫣 唐嫣 24301532 200 2391 明星名称 名称微博名 粉丝数
     * 关注 微博数
     * @param key 默认情况下，是mapreduce所读取到的一行文本的起始偏移量
     * @param value 默认情况下，是mapreduce所读取到的一行文本的内容，hadoop中的序列化类型为Text
     * @param context 是用户自定义逻辑处理完成后输出的KEY
     */
    @Override
    protected void map(Text key, Weibo value, Context context) throws IOException, InterruptedException {
        // 输出格式 key = friends values =
        // [{"friends":22898071,"followers":11,"num":268}...]
        context.write(new Text("friends"), new Text(key.toString() + "\t" + value.getFriends()));
        context.write(new Text("followers"), new Text(key.toString() + "\t" + value.getFollowers()));
        context.write(new Text("num"), new Text(key.toString() + "\t" + value.getNum()));
    }
}
