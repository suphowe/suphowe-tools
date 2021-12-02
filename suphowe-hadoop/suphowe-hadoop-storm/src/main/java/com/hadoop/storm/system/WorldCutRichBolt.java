package com.hadoop.storm.system;

import com.hadoop.storm.HadoopStormApplication;
import com.hadoop.storm.service.WorldService;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 单词统计Bolt，继承BaseRichBolt抽象类，如果要实现ack机制，在execute方法处理完后，要手动执行ack方法或者fail方法
 *
 * @Author:190503
 * @Since:2019年5月28日
 * @Version:1.1.0
 */
public class WorldCutRichBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    /**
     ** 在Bolt启动前执行，提供Bolt启动环境配置的入口 一般对于不可序列化的对象进行实例化。
     */
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        //启动spring容器
        HadoopStormApplication.run();
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        try {
            String world = input.getStringByField("world");
            //要使用spring容器的bean对象，不能直接用注解的方式，只能通过ApplicationContext获取
            WorldService worldService = (WorldService)SpringBeanUtils.getBean("worldService");
            char[] charArrays = worldService.worldCut(world);
            for (char c : charArrays) {
                collector.emit(new Values(String.valueOf(c)));
            }
            collector.ack(input);
        } catch (Exception e) {
            e.printStackTrace();
            collector.fail(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        Fields fields = new Fields("char");
        declarer.declare(fields);
    }

}






