package com.example.fanout;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class FanoutApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend("fanout_exchange","","fanout_exchanges");
        System.out.println("发送了：fanout_exchanges");
    }

}
