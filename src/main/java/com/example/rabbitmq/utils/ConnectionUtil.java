package com.example.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    public static Connection getConnection()throws Exception{
        //创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //factory.setHost("172.24.217.250");
        factory.setHost("localhost");
        factory.setPort(5672);
        //创建虚拟主机
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setConnectionTimeout(99999);
        return factory.newConnection();
    }
}
