package com.qf.rabbitmqdemo;

import com.qf.entity.SendIndexMsg;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class RabbitmqDemoApplicationIndexQueueTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testIndexQueue() {

        // 1.准备四个消息
        SendIndexMsg sendIndexMsg1 = new SendIndexMsg(1, "A", -1); // 第一个消息的pid为-1
        SendIndexMsg sendIndexMsg2 = new SendIndexMsg(2, "B", 1); // 第一个消息的pid为-1
        SendIndexMsg sendIndexMsg3 = new SendIndexMsg(3, "C", 2); // 第一个消息的pid为-1
        SendIndexMsg sendIndexMsg4 = new SendIndexMsg(4, "D", 3); // 第一个消息的pid为-1

        // 2.把四个消息发送给MQ，模拟发送到MQ消息的顺序是乱的
        rabbitTemplate.convertAndSend("hello-exchange", "business.index", sendIndexMsg3);
        rabbitTemplate.convertAndSend("hello-exchange", "business.index", sendIndexMsg1);
        rabbitTemplate.convertAndSend("hello-exchange", "business.index", sendIndexMsg4);
        rabbitTemplate.convertAndSend("hello-exchange", "business.index", sendIndexMsg2);

        System.out.println("消息发送成功。。。。");


    }

}
