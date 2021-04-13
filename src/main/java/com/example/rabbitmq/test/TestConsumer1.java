package com.example.rabbitmq.test;

import com.example.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class TestConsumer1 {
    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TestProducer.TEST_TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(TestProducer.TEST_TOPIC_QUEUE1,true,false,false,null);
        channel.queueBind(TestProducer.TEST_TOPIC_QUEUE1,TestProducer.TEST_TOPIC_EXCHANGE,"test.input.*");
        channel.queueBind(TestProducer.TEST_TOPIC_QUEUE1,TestProducer.TEST_TOPIC_EXCHANGE,"test.*.three");
        channel.queueBind(TestProducer.TEST_TOPIC_QUEUE1,TestProducer.TEST_TOPIC_EXCHANGE,"test.*");
        channel.queueBind(TestProducer.TEST_TOPIC_QUEUE1,TestProducer.TEST_TOPIC_EXCHANGE,"*.select.*");
        DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key为：" + envelope.getRoutingKey());
                System.out.println("交换机为：" + envelope.getExchange());
                System.out.println("消息id为：" + envelope.getDeliveryTag());
                System.out.println("接收到的消息为：" + new String(body, "utf-8"));
            }
        };
        channel.basicConsume(TestProducer.TEST_TOPIC_QUEUE1,true,defaultConsumer);
    }
}
