package com.hadoop.storm.system;


import com.hadoop.storm.HadoopStormApplication;
import com.hadoop.storm.service.WorldService;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.FailedException;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 单词统计Bolt，继承BaseBasicBolt抽象类，执行成功时自动执行ack方法,但是只对FailedException异常捕获并自动执行fail方法，其他异常需自己处理
 * @author suphowe
 */
public class WorldCutBasicBolt extends BaseBasicBolt {

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        //启动spring容器
        HadoopStormApplication.run();
        super.prepare(stormConf, context);
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            //throw new Exception();
            String world = input.getStringByField("world");
            //要使用spring容器的bean对象，不能直接用注解的方式，只能通过ApplicationContext获取
            WorldService worldService = (WorldService)SpringBeanUtils.getBean("worldService");
            char[] charArrays = worldService.worldCut(world);
            for (char c : charArrays) {
                collector.emit(new Values(String.valueOf(c)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedException();
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        Fields fields = new Fields("char");
        declarer.declare(fields);
    }

}
