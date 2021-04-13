package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {
    public int transfer(@Param("accountNo") String accountNo, @Param("accountBalance") Double accountBalance);
}
