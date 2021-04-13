package com.example.demo.config;

import com.example.demo.domain.AccountInfo;
import com.example.demo.service.AccountInfoService;
import com.example.demo.utils.Constant;
import com.example.demo.utils.RedisService;
import io.netty.util.internal.ObjectUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * rabbitmq配置类
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Exchange getDeadExchange() {
        return ExchangeBuilder.topicExchange(Constant.TEST_DEAD_EXCHANGE).build();
    }

    @Bean
    public Queue getDeadQueue() {
        return QueueBuilder.durable(Constant.TEST_DEAD_QUEUE).deadLetterExchange(Constant.TEST_ITS_DEAD_EXCHANGE).deadLetterRoutingKey(Constant.TEST_ITS_DEAD_KEY).build();
    }

    @Bean
    public Binding bindingDead() {
        return BindingBuilder.bind(getDeadQueue()).to(getDeadExchange()).with(Constant.TEST_DEAD_KEY).noargs();
    }

    @Bean
    public Exchange getItSDeadExchange() {
        return ExchangeBuilder.topicExchange(Constant.TEST_ITS_DEAD_EXCHANGE).build();
    }

    @Bean
    public Queue getItSDeadQueue() {
        return QueueBuilder.durable(Constant.TEST_ITS_DEAD_QUEUE).build();
    }

    @Bean
    public Binding bindingItSDead() {
        return BindingBuilder.bind(getItSDeadQueue()).to(getItSDeadExchange()).with(Constant.TEST_ITS_DEAD_KEY).noargs();
    }

    @Bean
    public Exchange getExchange() {
        return ExchangeBuilder.topicExchange(Constant.TEST_EXCHANGE).build();
    }

    @Bean
    public Queue getQueue() {
        return QueueBuilder.durable(Constant.TEST_QUEUE).deadLetterExchange(Constant.TEST_DEAD_EXCHANGE).deadLetterRoutingKey(Constant.TEST_DEAD_KEY).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(getQueue()).to(getExchange()).with(Constant.TEST_KEY).noargs();
    }

    @Resource
    private RedisService redisService;
    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 重写RabbitTemplate--设置为原型模式--回调
     * @param connectionFactory
     * @return
     */
    @Bean("rabbit")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate1(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbit = new RabbitTemplate(connectionFactory);
        rabbit.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息发送成功：" + ack);
                System.out.println("相关数据：" + correlationData);
            } else {
                System.out.println("消息发送失败：" + ack);
                System.out.println("失败原因：" + cause);
                AccountInfo accountInfo = (AccountInfo) redisService.get(Constant.TEST_QUEUE + correlationData.getId());
                xiaoFei(accountInfo,correlationData.getId());
            }
        });
        //测试消息发送到队列失败
        //消息发送到队列失败时执行-----没有测试，不知道怎么让他失败
        rabbit.setReturnsCallback(returnedMessage -> {
            System.out.println("rabbit.setReturnsCallback----returnedMessage ->"+returnedMessage.getMessage());
            AccountInfo accountInfo = (AccountInfo) redisService.get(Constant.TEST_QUEUE + returnedMessage.getMessage().getMessageProperties().getHeader("spring_returned_message_correlation"));
            xiaoFei(accountInfo,String.valueOf(returnedMessage.getMessage().getMessageProperties().getDeliveryTag()));
        });
        rabbit.setMessageConverter(new SerializerMessageConverter());
        return rabbit;
    }

    public void xiaoFei(AccountInfo accountInfo,String id){
        if(ObjectUtils.isEmpty(accountInfo)){
            System.out.println("请不要重复消费！");
        }else {
            int resule = accountInfoService.transfer(accountInfo.getAccountNo(), -accountInfo.getAccountBalance());
            if (resule == 1) {
                redisService.remove(Constant.TEST_QUEUE + id);
                System.out.println("消息发送失败！回滚数据！");
            } else {
                System.out.println("消息发送失败！回滚数据失败！");
            }
        }
    }

}
