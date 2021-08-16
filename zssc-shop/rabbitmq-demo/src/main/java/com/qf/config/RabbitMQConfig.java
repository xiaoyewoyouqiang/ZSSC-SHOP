package com.qf.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // 业务交换机
    @Bean
    public TopicExchange helloExchange() {
        return new TopicExchange("hello-exchange", true, false, null);
    }

    // 业务队列
    @Bean
    public Queue helloQueue() {

        // 给业务队列中设置死信的交换机
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "dle-exchange"); // 死信的交换机
        map.put("x-dead-letter-routing-key", "dle.error"); // 死信路由键

        return new Queue("hello-queue", true, false, false, map);
    }

    // 设置队列的最大长度
    @Bean
    public Queue maxQueue() {

        // 给业务队列中设置死信的交换机
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", "dle-exchange"); // 死信的交换机
        map.put("x-dead-letter-routing-key", "dle.error"); // 死信路由键

        map.put("x-max-length", 10); // 该队列最大能存10个消息
        return new Queue("max-queue", true, false, false, map);
    }

    @Bean // 创建一个ttl为5s的一个队列
    public Queue ttl5Queue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", 5000); // 5s,单位是5s

        map.put("x-dead-letter-exchange", "dle-exchange"); // 死信的交换机
        map.put("x-dead-letter-routing-key", "dle.error"); // 死信路由键

        return new Queue("ttl-5-queue", true, false, false, map);
    }

    @Bean // 创建一个ttl为20s的一个队列
    public Queue ttl20Queue() {
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", 20000); // 20s

        map.put("x-dead-letter-exchange", "dle-exchange"); // 死信的交换机
        map.put("x-dead-letter-routing-key", "dle.error"); // 死信路由键

        return new Queue("ttl-20-queue", true, false, false, map);
    }

    @Bean
    public Queue indexQueue() {
        return new Queue("indexQueue", true, false, false);
    }

    @Bean
    public Binding helloExchangeTtl5Queue() {
        return BindingBuilder.bind(ttl5Queue()).to(helloExchange()).with("business.5");
    }

    @Bean
    public Binding helloExchangeTtl20Queue() {
        return BindingBuilder.bind(ttl20Queue()).to(helloExchange()).with("business.20");
    }


    // 把max-queue绑定到helloExchange
    @Bean
    public Binding helloExchangeToMaxQueue() {
        return BindingBuilder.bind(maxQueue()).to(helloExchange()).with("business.max");
    }

    @Bean
    public Binding helloExchangeToIndexQueue() {
        return BindingBuilder.bind(indexQueue()).to(helloExchange()).with("business.index");
    }


    // 把业务队列绑定到业务交换机上
    @Bean
    public Binding helloExchangeToHelloQueue() {
        return BindingBuilder.bind(helloQueue()).to(helloExchange()).with("business.hello");
    }

    // 死信交换机
    @Bean
    public TopicExchange dleExchnange() {
        return new TopicExchange("dle-exchange", true, false, null);
    }

    // 死信队列
    @Bean
    public Queue dleQueue() {
        return new Queue("dle-queue", true, false, false, null);
    }

    @Bean
    public Binding dleExchnangeToDelQeueue() {
        return BindingBuilder.bind(dleQueue()).to(dleExchnange()).with("dle.*");
    }


}
