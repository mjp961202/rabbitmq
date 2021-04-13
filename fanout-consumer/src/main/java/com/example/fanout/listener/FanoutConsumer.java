package com.example.fanout.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    @RabbitListener(queues = {"fanout_queue"})
    public void fanoutConsumer(String message){
        System.out.println("接收到的消息为："+message);
    }
}
