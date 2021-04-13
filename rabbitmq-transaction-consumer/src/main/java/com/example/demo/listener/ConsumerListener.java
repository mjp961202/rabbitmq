package com.example.demo.listener;

import com.example.demo.domain.AccountInfo;
import com.example.demo.service.AccountInfoService;
import com.example.demo.utils.Constant;
import com.example.demo.utils.RedisService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 消费者监听消费
 */
@Component
@RabbitListener(queues = Constant.TEST_QUEUE)
public class ConsumerListener {

    @Resource
    private RedisService redisService;
    @Resource
    private AccountInfoService accountInfoService;

    @RabbitHandler
    public void transferListener(AccountInfo accountInfo, Message message, Channel channel) {
        try {
            //测试消息接收失败
            //if(true){throw new IOException();}
            if (redisService.exists(Constant.TEST_QUEUE + message.getMessageProperties().getHeaders().get("spring_returned_message_correlation"))) {
                //测试数据修改失败
                //int transfer=2;
                int transfer = accountInfoService.transfer(accountInfo.getAccountNoTwo(), accountInfo.getAccountBalance());
                if (transfer == 1) {
                    redisService.remove(Constant.TEST_QUEUE + message.getMessageProperties().getHeader("spring_returned_message_correlation"));
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    System.out.println("转账成功");
                } else {
                    throw new IOException();
                }
            } else {
                System.out.println("请不要重复消费！");
            }
        } catch (IOException e) {
            try {
                //消息失败，重试一次，失败则进入死信队列
                if (message.getMessageProperties().getRedelivered()) {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    System.out.println("转账失败！发生异常！消息进入死信队列！");
                } else {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    System.out.println("转账失败！发生异常！组织消息重新入队！");
                }
            } catch (IOException ex) {
                e.getMessage();
            }
        }
    }
}
