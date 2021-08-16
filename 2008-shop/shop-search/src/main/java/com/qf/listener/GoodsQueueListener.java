package com.qf.listener;

import com.qf.constants.ShopConstants;
import com.qf.entity.Goods;
import com.qf.service.IGoodsSearchService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class GoodsQueueListener {

    @Autowired
    private IGoodsSearchService goodsSearchService;
//
//    // 创建一个线程池
//    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @RabbitListener(queues = ShopConstants.GOODS_QUEUE)
    public void addGoodsToEs(Goods goods, Channel channel, Message message) {
//        log.debug("{}",goods);
        try {
            // 把商品添加到ES中
            goodsSearchService.addGoods(goods);
            // 手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // 把商品添加到ES中
//                    goodsSearchService.addGoods(goods);
//                    // 手动ack
//                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }
}
