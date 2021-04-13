package com.example.direct.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"direct_queue2"})
public class DirectConsumer2 {

    @RabbitHandler
    public void directConsumer(String message){
        System.out.println("direct_queue2接收消息："+message);
    }

}
