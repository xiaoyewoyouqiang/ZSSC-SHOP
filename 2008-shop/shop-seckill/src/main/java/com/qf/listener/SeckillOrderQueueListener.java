package com.qf.listener;

import com.qf.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

//@Configuration
public class SeckillOrderQueueListener {

//    @RabbitListener(queues = "seckill-order-queue")
//    public void readerOrderMsg(Order order, Channel channel, Message message) throws InterruptedException, IOException {
//
//        // 1、先输出订单的消息
//        System.out.println(order);
//
//        // 2.模拟消费这个订单需要1s的时间
//        Thread.sleep(1000);
//
//        // 3.手动ack
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }
}
