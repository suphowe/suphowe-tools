package com.hadoop.storm.system;

import com.hadoop.storm.HadoopStormApplication;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 字母统计Spout 要开启ack机制，必须设置setNumAckers>0（即acker处理线程数量大于0），并且传递消息时，带上msgId
 *
 * @Author:190503
 * @Since:2019年5月28日
 * @Version:1.1.0
 */
public class WorldCountSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    private Queue<String> wordQueue = new ConcurrentLinkedQueue<String>();

    /**
     ** 发送失败集合，用于重发
     */
    private Map<String, Object> failMap = new ConcurrentHashMap<String, Object>();

    @Override
    /**
     * open()方法中是在ISpout接口中定义，在Spout组件初始化时被调用。 有三个参数: 1.Storm配置的Map;
     * 2.topology中组件的信息; 3.发射tuple的方法;
     */
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {

        System.out.println(conf.get("myconfParam"));
        wordQueue.add("Hello");
        wordQueue.add("World");
        wordQueue.add("Drew");
        //启动spring容器
        HadoopStormApplication.run();
        this.collector = collector;
    }

    @Override
    /**
     * nextTuple()方法是Spout实现的核心。 也就是主要执行方法，用于输出信息,通过collector.emit方法发射。
     */
    public void nextTuple() {
        while (!wordQueue.isEmpty()) {
            String world = wordQueue.poll();
            if (Optional.ofNullable(world).isPresent()) {
                //collector.emit(new Values(world));//不传msgId
                //传递消息时加上msgId，用于定位消息
                String key = UUID.randomUUID().toString().replace("-", "");
                //记录消息，方便失败重发
                failMap.put(key, world);
                collector.emit(new Values(world), key);
            }
        }

    }

    @Override
    /**
     * declareOutputFields是在IComponent接口中定义，用于声明数据格式。 即输出的一个Tuple中，包含几个字段。
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        Fields fields = new Fields("world");
        declarer.declare(fields);
    }

    /**
     ** 当一个Tuple处理成功时，会调用这个方法 param obj emit方法中的msgId
     */
    @Override
    public void ack(Object obj) {
        //清除消息
        failMap.remove(obj);
        System.out.println("成功：" + obj);
    }

    /**
     ** 当Topology停止时，会调用这个方法
     */
    @Override
    public void close() {
        System.out.println("关闭...");
    }

    /**
     ** 当一个Tuple处理失败时，会调用这个方法
     */
    @Override
    public void fail(Object obj) {
        System.out.println("失败：" + obj);
        String world = (String)failMap.get(obj);
        //清除消息，只重发一次
        failMap.remove(obj);
        if (Optional.ofNullable(world).isPresent()) {
            collector.emit(new Values(world));
        }
    }
}
