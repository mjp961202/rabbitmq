package com.example.ttl.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTtlConfig {

    @Bean
    public Exchange getExchange() {
        return ExchangeBuilder.topicExchange("ttl_exchange").build();
    }

    @Bean
    public Exchange getDeadExchange(){
        return ExchangeBuilder.topicExchange("dead_exchange").build();
    }

    @Bean
    public Queue getDeadQueue(){
        return QueueBuilder.durable("dead_queue").build();
    }

    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(getDeadQueue()).to(getDeadExchange()).with("dead").noargs();
    }

    @Bean
    public Queue getQueue() {
        return QueueBuilder.durable("ttl_queue").ttl(15000).deadLetterExchange("dead_exchange").deadLetterRoutingKey("dead").build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(getQueue()).to(getExchange()).with("ttl").noargs();
    }
}
