package com.qf.rabbitmqdemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class RabbitmqDemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 给队列发送消息
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend("hello-exchange", "business.hello", "hello 死信队列");
    }

    // 测试最大队列
    // 1、发送20个消息出去，多余的10个去了哪里
    // 2.消费者消费的是那个10？
    @Test
    void testMaxQueue() throws IOException {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("hello-exchange", "business.max", "队列_" + i);
        }
        System.out.println("20个消息发送成功。。。");
        System.in.read();
    }

    @Test
    public void test() throws IOException {
        System.out.println("消费者读取消息中。。。。。。");
        System.in.read();
    }

}
