package com.example.direct;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DirectApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend("direct_exchange","direct1","direct_queue1");
        System.out.println("发送消息：direct_queue1");
        rabbitTemplate.convertAndSend("direct_exchange","direct2","direct_queue2");
        System.out.println("发送消息：direct_queue2");
    }

}
