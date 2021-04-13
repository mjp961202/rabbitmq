package com.example.rabbitmq.topic;

import com.example.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
    static final String TOPIC_EXCHAGE = "topic_exchange";
    static final String TOPIC_QUEUE_1 = "topic_queue_1";
    static final String TOPIC_QUEUE_2 = "topic_queue_2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_EXCHAGE, BuiltinExchangeType.TOPIC);
        String message1 = "新增了商品。Topic模式；routing key 为 item.insert ";
        channel.basicPublish(TOPIC_EXCHAGE, "item.insert", null, message1.getBytes());
        System.out.println("发送消息：{}" + message1);
        String message2 = "修改了商品。Topic模式；routing key 为 item.update";
        channel.basicPublish(TOPIC_EXCHAGE, "item.update", null, message2.getBytes());
        System.out.println("发送消息：{}" + message2);
        String message3 = "删除了商品。Topic模式；routing key 为 item.delete";
        channel.basicPublish(TOPIC_EXCHAGE, "item.delete", null, message3.getBytes());
        System.out.println("发送消息：{}" + message3);
        String message4 = "查询了商品。Topic模式；routing key 为 item.update";
        channel.basicPublish(TOPIC_EXCHAGE, "item.select", null, message4.getBytes());
        System.out.println("发送消息：{}" + message4);
        String message5 = "测试了商品。Topic模式；routing key 为 item.add.del.upd.sel";
        channel.basicPublish(TOPIC_EXCHAGE, "item.add.del.upd.sel", null, message5.getBytes());
        System.out.println("发送消息：{}" + message5);
        channel.close();
        connection.close();
    }
}
