package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.AccountInfo;
import com.example.demo.mapper.AccountInfoMapper;
import com.example.demo.service.AccountInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int transfer(String accountNo, Double accountBalance){
        return accountInfoMapper.transfer(accountNo, accountBalance);
    }
}
