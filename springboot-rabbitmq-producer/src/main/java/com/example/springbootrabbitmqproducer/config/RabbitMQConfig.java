package com.example.springbootrabbitmqproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ITEM_TOPIC_EXCHANGE ="item_topic_exchange";
    public static final String ITEM_QUEUE ="item_queue";

    @Bean(ITEM_TOPIC_EXCHANGE)
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean(ITEM_QUEUE)
    public Queue topicQueue(){
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    @Bean
    public Binding itemQueueExchange(@Qualifier(ITEM_QUEUE) Queue queue,@Qualifier(ITEM_TOPIC_EXCHANGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }
}
