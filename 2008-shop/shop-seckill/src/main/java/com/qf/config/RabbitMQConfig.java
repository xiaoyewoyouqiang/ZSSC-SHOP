package com.qf.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("seckill-order-exchange", true, false);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue("seckill-order-queue", true, false, false);
    }

    @Bean
    public Binding orderExchangeToOrderQueue() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.*");
    }
}
