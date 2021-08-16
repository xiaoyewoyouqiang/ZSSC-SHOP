package com.qf.listener;

import com.qf.constants.ShopConstants;
import com.qf.entity.Goods;
import com.qf.entity.GoodsPic;
import com.rabbitmq.client.Channel;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class ItemQueueListener {
    @Autowired
    private freemarker.template.Configuration configuration;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @RabbitListener(queues = ShopConstants.ITEM_QUEUE)
    public void createItem(Goods goods, Channel channel, Message message) throws IOException, TemplateException {

        executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
//                log.debug("{}", goods);
                // 1、获取模板
                Template template = configuration.getTemplate("goodsItemTemplate.ftl");

                // 2.准备数据
                Map<String, Object> map = new HashMap<>();
                map.put("gname", goods.getGname());
                map.put("gprice", goods.getGprice());
                map.put("pngList", goods.getTempPng().split("\\|"));

                // 3.准备静态也面输出的位置
                String path = ItemQueueListener.class.getClassLoader().getResource("static").getPath();
                path = URLDecoder.decode(path, "UTF-8");
//                System.out.println(path);
                // 4、生成静态也面
                template.process(map, new FileWriter(path + File.separator + goods.getId() + ".html"));

//                log.debug("{}", path);
                // 5、手动ack
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


//    @RabbitListener(queues = "mq-demo")
//    public void testMQDemo(String msg, Channel channel, Message message) {
//
//        // 每次分配给消息给消费者后，启动一个子线程去帮我消费这个消息，
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("该消息【" + msg + "】正在消费中。。。。");
//                // 模拟消费完这个消息需要2s
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                // 手动ack
//                try {
//                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//    }
}
