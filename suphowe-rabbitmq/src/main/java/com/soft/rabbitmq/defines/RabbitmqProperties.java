package com.soft.rabbitmq.defines;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * rabbitmq 配置类
 * @author suphowe
 */
@Component
@Data
@PropertySource(value= {"classpath:properties/rabbitmq.properties"})
public class RabbitmqProperties {

    @Value("${direct.queue}")
    private String directQueue;

    @Value("${direct.exchange}")
    private String directExchange;

    @Value("${direct.lonelyexchange}")
    private String lonelyDirectExchange;

    @Value("${direct.routing}")
    private String directRouting;

    @Value("${fanout.queue.main}")
    private String fanoutQueueMain;

    @Value("${fanout.queue.secondary}")
    private String fanoutQueueSecondary;

    @Value("${fanout.queue.third}")
    private String fanoutQueueThird;

    @Value("${fanout.exchange}")
    private String fanoutExchange;

    @Value("${fanout.routing}")
    private String fanoutRouting;

    @Value("${topic.queue.main}")
    private String mainTopicQueue;

    @Value("${topic.queue.secondary}")
    private String secondaryTopicQueue;

    @Value("${topic.exchange}")
    private String topicExchange;

    @Value("${topic.routing}")
    private String topicRouting;

    @Value("${receiver.concurrentconsumers}")
    private int concurrentConsumers;

    @Value("${receiver.maxconcurrentconsumers}")
    private int maxConcurrentConsumers;
}
