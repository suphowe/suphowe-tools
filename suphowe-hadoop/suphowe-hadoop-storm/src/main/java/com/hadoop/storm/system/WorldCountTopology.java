package com.hadoop.storm.system;

import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

public class WorldCountTopology {

    public static void main(String[] args)
            throws Exception {

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        //parallelism_hint 执行线程数 setNumTasks 所有线程运行任务总数，以下配置是2个spout线程各自运行一个任务
        topologyBuilder.setSpout("worldCountSpout", new WorldCountSpout(), 2).setNumTasks(2);
        //topologyBuilder.setBolt("worldCutBolt", new WorldCutRichBolt(), 2).shuffleGrouping("worldCountSpout");
        //tuple随机分发给下一阶段的bolt ; parallelism_hint 执行线程数  ;  setNumTasks 所有线程运行任务总数，以下配置是2个线程各自运行一个Bolt任务
        topologyBuilder.setBolt("worldCutBolt", new WorldCutBasicBolt(), 2)
                .setNumTasks(2)
                .shuffleGrouping("worldCountSpout");
        //tuple按字段char的值分发给下一阶段的bolt
        topologyBuilder.setBolt("worldCountBolt", new WorldCountBolt(), 2)
                .fieldsGrouping("worldCutBolt", new Fields("char"));
        Config conf = new Config();
        //关闭ack
        //conf.setNumAckers(0);
        conf.put("myconfParam", "test");
        //本地模式
        /*LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("myTopology", conf, topologyBuilder.createTopology());
        //  关闭本地集群
        Thread.sleep(10000);
        cluster.shutdown();*/

        //集群模式
        StormSubmitter.submitTopology("myTopology", conf, topologyBuilder.createTopology());
    }
}
