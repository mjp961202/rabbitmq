package com.example.rabbitmq.routing;

import com.example.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {

    public static final String DIRECT_EXCHAGE = "direct_exchange";
    public static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    public static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(DIRECT_EXCHAGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DIRECT_QUEUE_INSERT, true, false, false, null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE, true, false, false, null);
        channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHAGE, "insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHAGE, "update");
        String message1 = "新增了商品。路由模式；routing key 为 insert ";
        channel.basicPublish(DIRECT_EXCHAGE, "insert", null, message1.getBytes());
        System.out.println("已发送消息：" + message1);
        String message2 = "修改了商品。路由模式；routing key 为 update";
        channel.basicPublish(DIRECT_EXCHAGE, "update", null, message2.getBytes());
        System.out.println("已发送消息：" + message2);
        channel.close();
        connection.close();
    }
}
