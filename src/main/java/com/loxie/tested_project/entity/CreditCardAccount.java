package com.loxie.tested_project.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditCardAccount {
    private String cardNo;
    private String personName;
    private BigDecimal usableAmount;
    private BigDecimal overAmount;
    private String cardPin;
    private String cardExpiredDate;
    private String cerType;
    private String cerNo;
    private Integer trxZoneno; // 开卡地区，用于模拟异地交易
    private String cardStatus = "1"; // 卡状态 1-正常 0-异常
    private BigDecimal dailyLimit = new BigDecimal("10000"); // 单日交易限额
}
