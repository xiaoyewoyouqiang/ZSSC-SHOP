package com.qf.listener;

import com.qf.entity.SendIndexMsg;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class HelloQueueListener {

    @RabbitListener(queues = "hello-queue")
    public void readerMsg(String msg, Channel channel, Message message) {

        // 消费这个消息中
        System.out.println("消费者拉取的消息:" + msg);

        try {
            // 模拟消费的过程中出现了异常
            int i = 10 / 0;

            // 手动ack
            // 第一个参数是消息的唯一标识
            // 第二个参数不需要批确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.out.println("消费的过程中出现了异常。。。");

            // 告诉MQ这个消息我无法消费，所以要nack
            try {
                // 第三个参数是否把这个消息重新加入队列,false不会加入队列，如果业务队列中配置了死信属性，这个消息就会变成死信
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //@RabbitListener(queues = "dle-queue")
//    public void readerDelQueue(String msg, Channel channel, Message message) throws IOException {
//        System.out.println("死信队列的消费者(只做消息的记录，说明这些消息没有被消费):" + msg);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 给死信队列手动ack
//    }

    @RabbitListener(queues = "dle-queue")
    public void readerDelQueue(String msg, Channel channel, Message message) throws IOException {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("死信接收到的时间是:" + format + "," + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 给死信队列手动ack
    }

    //  @RabbitListener(queues = "max-queue")
    public void readerMaxQueue(String msg, Channel channel, Message message) throws IOException {
        System.out.println("最大队列读取的消息:" + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 给死信队列手动ack
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "indexQueue")
    public void readerIndexQueueMsg(SendIndexMsg sendIndexMsg, Channel channel, Message message) throws IOException {

        // 1.先判断这个消息是否是首个需要消费的消息
        if (sendIndexMsg.getPid() == -1) {
            // 如果消息的pid为-1，说明消息是首个需要消费的

            // 消费这个消息
            System.out.println(sendIndexMsg);

            // 记录这个消息已经被消费了
            stringRedisTemplate.opsForValue().set(sendIndexMsg.getId().toString(), "1");

            // 然后告诉MQ这个消息已经被成功消费了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {

            // 获取当前消息的pid
            Integer pid = sendIndexMsg.getPid();

            // 查看该消息的上个消息是否已经被成功消费
            String pIdStatus = stringRedisTemplate.opsForValue().get(pid.toString());

            if (!StringUtils.isEmpty(pIdStatus) && "1".equals(pIdStatus)) {
                // 说明pid已经被消费了，该消息可以被消费

                // 注册消费这个消息
                System.out.println(sendIndexMsg);

                // 记录该消息已经被成功消费了
                stringRedisTemplate.opsForValue().set(sendIndexMsg.getId().toString(), "1");

                // 然后告诉MQ这个消息已经被成功消费了
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                // 这个消息的上一个消息没有被消费，所以该消息不能被消费，
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}
