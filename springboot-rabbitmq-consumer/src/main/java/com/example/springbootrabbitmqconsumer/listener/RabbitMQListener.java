package com.example.springbootrabbitmqconsumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "item_queue")
    public void msgListener(String message){
        System.out.println("接收到的消息为："+message);
    }
}
