package com.example.rabbitmq.routing;

import com.example.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Producer.DIRECT_EXCHAGE,BuiltinExchangeType.DIRECT);
        channel.queueDeclare(Producer.DIRECT_QUEUE_INSERT, true, false, false, null);
        channel.queueBind(Producer.DIRECT_QUEUE_INSERT,Producer.DIRECT_EXCHAGE,"insert");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("路由key为：" + envelope.getRoutingKey());
                    System.out.println("交换机为：" + envelope.getExchange());
                    System.out.println("消息id为：" + envelope.getDeliveryTag());
                    System.out.println("接收到的消息为：" + new String(body, "utf-8"));
                    System.out.println(consumerTag);
            }
        };
        channel.basicConsume(Producer.DIRECT_QUEUE_INSERT, true, consumer);
        System.out.println(consumer.toString());
        System.out.println("======================================");
    }
}
