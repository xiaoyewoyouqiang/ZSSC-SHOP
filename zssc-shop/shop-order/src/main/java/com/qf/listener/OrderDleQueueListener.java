package com.qf.listener;

import com.qf.constants.ShopConstants;
import com.qf.entity.Order;
import com.qf.entity.SeckillGoods;
import com.qf.service.IOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class OrderDleQueueListener {

//    @Autowired
//    private IOrderService orderService;

    // 监听正常下单的队列
    @RabbitListener(queues = ShopConstants.ORDER_DLE_QUEUE)
    public void updateOrderStatus(Order order, Channel channel, Message message) {
        System.out.println("订单已经超时了。。。。");

        // 1.根据订单id获取用户id后四位
        String userIdSuffix = order.getId().substring(8, 8 + 4);

        // 2.根据订单id查询最新的订单状态
        Order newOrder = orderService.getOrderById(Integer.parseInt(userIdSuffix), order.getId());

        // 3.判断订单状态是否是未支付
        if (1 == newOrder.getStatus()) {
            // 说明订单目前还是未支付状态，可以改为已超时
            System.out.println("说明订单目前还是未支付状态，可以改为已超时");
            Map<String, String> map = new HashMap<>();
            map.put("orderId", newOrder.getId());
            map.put("status", "4");

            orderService.updateOrderStatus(map); // 更新订单状态

            // 订单超时或者订单取消需要返回库存

            // 根据订单id查询订单详情

            // 有了订单的详情，就可以获取到每个商品购买的数量

            // 更新库存

            System.out.println("修改订单超时状态完成。。。");
        } else {
            System.out.println("订单不是未支付的状态，无法改超时状态");
        }

        try {
            //手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private IOrderService orderService;

    // 监听秒杀系统的订单队列
//    @RabbitListener(queues = "seckill-order-queue")
//    public void readerOrderMsg(SeckillGoods seckillGoods, Channel channel, Message message) throws InterruptedException, IOException {
//
//        try {
//            // 1.生成秒杀的订单
//            orderService.createSeckillOrder(seckillGoods);
//
//            // 2.手动ack
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (Exception e) {
//            log.error("生成秒杀订单失败。。", e);
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//        }
//    }
}
