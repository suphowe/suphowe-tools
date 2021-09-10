package com.soft.rabbitmq.exchange;

import com.soft.rabbitmq.defines.RabbitmqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 扇型交换机
 * @author suphowe
 */
@Configuration
public class FanoutExchangeRabbitConfig {

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    /*
     *  创建三个队列 ：fanoutQueue.main   fanoutQueue.secondary  fanoutQueue.third
     *  将三个队列都绑定在交换机 fanoutExchange 上
     *  因为是扇型交换机, 路由键无需配置,配置也不起作用
     */

    @Bean
    public Queue fanoutQueueMain() {
        return new Queue(rabbitmqProperties.getFanoutQueueMain());
    }

    @Bean
    public Queue fanoutQueueSecondary() {
        return new Queue(rabbitmqProperties.getFanoutQueueSecondary());
    }

    @Bean
    public Queue fanoutQueueThird() {
        return new Queue(rabbitmqProperties.getFanoutQueueThird());
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(rabbitmqProperties.getFanoutExchange());
    }

    @Bean
    Binding bindingExchangeMain() {
        return BindingBuilder.bind(fanoutQueueMain()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeSecondary() {
        return BindingBuilder.bind(fanoutQueueSecondary()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeThird() {
        return BindingBuilder.bind(fanoutQueueThird()).to(fanoutExchange());
    }
}
