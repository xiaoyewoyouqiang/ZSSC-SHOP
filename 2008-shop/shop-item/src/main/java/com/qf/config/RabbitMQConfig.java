package com.qf.config;

import com.qf.constants.ShopConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 1.创建一个用来方商品的队列
    @Bean
    public Queue itemQueue(){
        return new Queue(ShopConstants.ITEM_QUEUE,true,false,false);
    }

    // 2.创建一个交换机
    @Bean
    public TopicExchange goodsExchange(){
        return new TopicExchange(ShopConstants.GOODS_EXCHANGE,true,false);
    }

    // 3.把队列绑定到交换机上面
    @Bean
    public Binding itemQueueTogoodsExchange(){
        Binding with = BindingBuilder.bind(itemQueue()).to(goodsExchange()).with("goods.*");
        return with;
    }


}
