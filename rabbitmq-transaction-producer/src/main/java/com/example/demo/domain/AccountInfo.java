package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("account_info")
public class AccountInfo implements Serializable {
    @TableField("id")
    private Long id;
    @TableField("account_name")
    private String accountName;
    @TableField("account_no")
    private String accountNo;
    @TableField("account_balance")
    private Double accountBalance;
    @TableField(exist = false)
    private String accountNoTwo;
}
