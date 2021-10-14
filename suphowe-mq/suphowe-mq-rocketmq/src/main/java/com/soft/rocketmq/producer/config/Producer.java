package com.soft.rocketmq.producer.config;

import com.soft.rocketmq.constant.Constants;
import com.soft.rocketmq.producer.defines.ProducerProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rocketmq 生产者
 * @author suphowe
 */
@Component
public class Producer {

    private DefaultMQProducer defaultMQProducer;

    public Producer(){
        //示例生产者
        defaultMQProducer = new DefaultMQProducer(Constants.PRODUCER_GROUPNAME);
        //不开启vip通道 开通口端口会减2
        defaultMQProducer.setVipChannelEnabled(false);
        //绑定name server
        defaultMQProducer.setNamesrvAddr(Constants.PRODUCER_NAMESERVERS);
        start();
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start(){
        try {
            this.defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public DefaultMQProducer getProducer(){
        return this.defaultMQProducer;
    }

    /**
     * 一般在应用上下文，使用上下文监听器，进行关闭
     */
    public void shutdown(){
        this.defaultMQProducer.shutdown();
    }
}
