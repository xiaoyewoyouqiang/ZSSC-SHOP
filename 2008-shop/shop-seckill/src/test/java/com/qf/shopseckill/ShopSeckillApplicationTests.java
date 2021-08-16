package com.qf.shopseckill;

import com.qf.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Date;

@SpringBootTest
class ShopSeckillApplicationTests {

    @Test
    void contextLoads() {

        String key = "msg_%s_%s";

        String format = String.format(key, 10, 20);
        System.out.println(format);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testExckillOrderQueue() {

        for (int i = 0; i < 2000; i++) {
            Order order = new Order(null, 10, "name_" + i, new Date(),10);
            rabbitTemplate.convertAndSend("seckill-order-exchange", "order.create", order);
        }
        System.out.println("队列添加数据完成。。。");
    }

    @Test
    public void test() throws IOException {
        System.out.println("ShopSeckillApplicationTests.test");
        System.in.read();
    }

}
