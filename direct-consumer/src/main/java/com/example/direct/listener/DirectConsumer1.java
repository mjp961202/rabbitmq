package com.example.direct.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"direct_queue1"})
public class DirectConsumer1 {

    @RabbitHandler
    public void directConsumer(String message){
        System.out.println("direct_queue1接收消息："+message);
    }

}
