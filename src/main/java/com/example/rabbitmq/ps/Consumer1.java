package com.example.rabbitmq.ps;

import com.example.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE,BuiltinExchangeType.FANOUT);
        channel.queueDeclare(Producer.FANOUT_QUEUE1, true, false, false, null);
        channel.queueBind(Producer.FANOUT_QUEUE1,Producer.FANOUT_EXCHANGE,"");
        //创建消费者；并设置消息处理
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             * consumerTag 消息者标签，在channel.basicConsume时候可以指定
             * envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
             * properties 属性信息
             * body 消息
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key为：" + envelope.getRoutingKey());
                System.out.println("交换机为：" + envelope.getExchange());
                System.out.println("消息id为：" + envelope.getDeliveryTag());
                System.out.println("接收到的消息为：" + new String(body, "utf-8"));
                System.out.println(consumerTag);
            }
        };
        //监听消息
        /**
         * 参数1：队列名称
         * 参数2：是否自动确认，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动确认
         * 参数3：消息接收到后回调
         */
        channel.basicConsume(Producer.FANOUT_QUEUE1, true, consumer);
        System.out.println(consumer.toString());
        System.out.println("======================================");
    }
}
