package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.AccountInfo;

public interface AccountInfoService extends IService<AccountInfo> {

    public int transfer(String accountNo,Double accountBalance);
}
