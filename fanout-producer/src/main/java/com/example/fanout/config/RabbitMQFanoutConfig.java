package com.example.fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQFanoutConfig {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout_exchange",true,false);
    }

    @Bean
    public Queue fanoutQueue(){
        return new Queue("fanout_queue",true);
    }

    @Bean
    public Binding fanoutBinding(){
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
}
