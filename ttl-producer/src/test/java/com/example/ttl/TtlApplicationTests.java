package com.example.ttl;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TtlApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        /**
         * 设置消息过期
         * 单独设置消息的过期时间，需要把消息放在前面
         */
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("4000");
        Message message = new Message("ttl消息".getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("ttl_exchange","ttl",message);

        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("ttl_exchange","ttl","ttl_queue"+i);
            System.out.println("发送了带时间消息：ttl_queue"+i);
        }
    }

}
