package com.example.demo;

import com.example.demo.mapper.AccountInfoMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Test
    void contextLoads() {
        accountInfoMapper.transfer("1",10d);
    }

}
