package com.soft.rabbitmq.exchange;

import com.soft.rabbitmq.defines.RabbitmqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 主题交换机
 * @author suphowe
 */
@Configuration
public class TopicExchangeRabbitConfig {

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Bean
    public Queue mainQueue() {
        return new Queue(rabbitmqProperties.getMainTopicQueue());
    }

    @Bean
    public Queue secondaryQueue() {
        return new Queue(rabbitmqProperties.getSecondaryTopicQueue());
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rabbitmqProperties.getTopicExchange());
    }


    // 将mainQueue和topicExchange绑定,而且绑定的键值为topic.main
    // 这样只要是消息携带的路由键是topic.main,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(mainQueue()).to(exchange()).with(rabbitmqProperties.getMainTopicQueue());
    }

    // 将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondaryQueue()).to(exchange()).with("topic.#");
    }
}
