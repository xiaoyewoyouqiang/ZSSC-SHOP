package com.qf.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMsg")
    public String sendMsg(String time) {
        System.out.println("发送消息的时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if ("5".equals(time)) {
            rabbitTemplate.convertAndSend("hello-exchange", "business.5", "这是5s的延时队列");
        } else if ("20".equals(time)) {
            rabbitTemplate.convertAndSend("hello-exchange", "business.20", "这是20的延时队列");
        }
        return "ok";
    }
}
