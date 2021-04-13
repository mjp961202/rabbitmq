package com.example.rabbitmq.test;

import com.example.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TestProducer {

    public static final String TEST_TOPIC_EXCHANGE="test_topic_exchange";
    public static final String TEST_TOPIC_QUEUE1="test_topic_queue1";
    public static final String TEST_TOPIC_QUEUE2="test_topic_queue2";
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(TEST_TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        String message1="新增1----------------------新增1";
        channel.basicPublish(TEST_TOPIC_EXCHANGE,"test.input.one",null,message1.getBytes());
        System.out.println(message1);
        String message2="新增2----------------------新增2";
        channel.basicPublish(TEST_TOPIC_EXCHANGE,"test.input.tow",null,message2.getBytes());
        System.out.println(message2);
        String message3="修改3----------------------修改3";
        channel.basicPublish(TEST_TOPIC_EXCHANGE,"test.update.three",null,message3.getBytes());
        System.out.println(message3);
        String message4="删除4----------------------删除4";
        channel.basicPublish(TEST_TOPIC_EXCHANGE,"test.delete.for",null,message4.getBytes());
        System.out.println(message4);
        String message5="查询5----------------------查询5";
        channel.basicPublish(TEST_TOPIC_EXCHANGE,"select.test.save",null,message5.getBytes());
        System.out.println(message5);
        channel.close();
        connection.close();
    }
}
