package com.example.direct.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDirectConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange", true, false);
    }

    @Bean
    public Queue directQueue1() {
        return new Queue("direct_queue1", true);
    }

    @Bean
    public Queue directQueue2() {
        return new Queue("direct_queue2", true);
    }

    @Bean
    public Binding bindingBuilder1() {
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("direct1");
    }

    @Bean
    public Binding bindingBuilder2() {
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("direct2");
    }
}
