package com.hadoop.storm.system;

import com.hadoop.storm.HadoopStormApplication;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class WorldCountBolt extends BaseBasicBolt {

    private Map<String, Integer> worldcountMap = new ConcurrentHashMap<String, Integer>();

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        //启动spring容器
        HadoopStormApplication.run();
        super.prepare(stormConf, context);
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String c = input.getStringByField("char");
        int count = worldcountMap.getOrDefault(c, 0) + 1;
        worldcountMap.put(c, count);
        System.out.println("线程:" + Thread.currentThread().getName() + "字符" + c + "目前个数为" + count);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}