package com.example.demo.controller;

import com.example.demo.domain.AccountInfo;
import com.example.demo.service.AccountInfoService;
import com.example.demo.utils.Constant;
import com.example.demo.utils.RedisService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 生产者发送消息
 */
@RestController
@RequestMapping("/producer")
public class AccountInfoController {

    @Resource
    private RedisService redisService;
    @Autowired
    @Qualifier("rabbit")
    private RabbitTemplate rabbitTemplate;
    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 转账
     *
     * @return
     */
    @PostMapping("/transfer")
    public void transfer(@RequestBody AccountInfo accountInfo){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        try {
            if (redisService.setIfAbsent(Constant.TEST_QUEUE + correlationData.getId(), accountInfo, 1, TimeUnit.DAYS)) {
                //测试数据修改失败
                //int result = 2;
                int result = accountInfoService.transfer(accountInfo.getAccountNo(), accountInfo.getAccountBalance());
                if (result == 1) {
                    //测试发送到错误的交换机
                    //rabbitTemplate.convertAndSend("test", Constant.TEST_KEY, accountInfo, correlationData);
                    rabbitTemplate.convertAndSend(Constant.TEST_EXCHANGE, Constant.TEST_KEY, accountInfo, correlationData);
                    System.out.println("扣款成功！" + result);
                } else {
                    redisService.remove(Constant.TEST_QUEUE + correlationData.getId());
                    System.out.println("扣款失败！" + result);
                }
            } else {
                System.out.println("请不要重复发起转账！");
            }
        } catch (Exception e) {
            redisService.remove(Constant.TEST_QUEUE + correlationData.getId());
        }
    }

}
