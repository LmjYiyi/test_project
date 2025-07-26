package com.loxie.tested_project.service.impl;

import com.loxie.tested_project.entity.CreditCardAccount;
import com.loxie.tested_project.model.CreditCardTransferRequest;
import com.loxie.tested_project.model.ResponseData;
import com.loxie.tested_project.model.SubField2;
import com.loxie.tested_project.repository.CreditCardAccountMapper;
import com.loxie.tested_project.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardAccountMapper creditCardAccountMapper;

    @Override
    @Transactional
    public ResponseData transferPay(CreditCardTransferRequest request) {
        String cardNo = request.getCardChkParams().getCard_no();
        String counterAccountNo = request.getCounterParams().getCounter_account();
        BigDecimal transferAmount = request.getTransfer_amount();
        BigDecimal fee = BigDecimal.ZERO;

        log.info("Transfer request received for card: {}, amount: {}, counter account: {}", cardNo, transferAmount,
                counterAccountNo);

        CreditCardAccount account = creditCardAccountMapper.findByCardNo(cardNo);

        if (account == null) {
            log.warn("Card not found: {}", cardNo);
            return ResponseData.fail("ERR1004", "该卡不存在", null, cardNo, BigDecimal.ZERO, BigDecimal.ZERO);
        }
        log.info("Before transfer - Card: {}, Usable: {}, Over: {}", cardNo, account.getUsableAmount(),
                account.getOverAmount());

        // 异地交易检查
        if (!request.getInfoParams().getTrx_zoneno().equals(account.getTrxZoneno())
                && !"1".equals(request.getChk_local_flag())) {
            return ResponseData.fail("ERR1002", "不支持异地交易", null, cardNo, account.getUsableAmount(),
                    account.getOverAmount());
        }

        // 外币交易检查
        if ("1".equals(request.getTransfer_currency_type()) && request.getTransfer_currency() == 156) {
            return ResponseData.fail("ERR1003", "外币交易币种错误", null, cardNo, account.getUsableAmount(),
                    account.getOverAmount());
        }
        if ("0".equals(request.getTransfer_currency_type()) && request.getTransfer_currency() != 156) {
            return ResponseData.fail("ERR1003", "人民币交易币种错误", null, cardNo, account.getUsableAmount(),
                    account.getOverAmount());
        }

        // 客户身份检查场景
        // 1. 姓名检查
        if ("1".equals(request.getChk_name_flag())) {
            if (request.getPerson_name() == null || request.getPerson_name().isEmpty()) {
                return ResponseData.fail("ERR1003", "姓名不能为空", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
            if (!request.getPerson_name().equals(account.getPersonName())) {
                return ResponseData.fail("ERR1003", "校验身份信息失败", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
        }

        // 2. 身份证信息验证
        if ("1".equals(request.getCerChkParams().getCer_chk())) {
            if (request.getCerChkParams().getCer_no() == null || request.getCerChkParams().getCer_no().isEmpty()
                    || request.getCerChkParams().getCer_type() == null
                    || request.getCerChkParams().getCer_type().isEmpty()) {
                return ResponseData.fail("ERR1003", "证件号码和类型不能为空", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
            if (!request.getCerChkParams().getCer_no().equals(account.getCerNo())
                    || !request.getCerChkParams().getCer_type().equals(account.getCerType())) {
                return ResponseData.fail("ERR1003", "校验身份信息失败", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
        }

        // 卡状态检查(错误码ERR1005)
        if (!"1".equals(account.getCardStatus())) {
            return ResponseData.fail("ERR1005", "该卡已销户", null, cardNo, account.getUsableAmount(),
                    account.getOverAmount());
        }

        // 卡密码检查(错误码ERR1005)
        if ("1".equals(request.getCardChkParams().getCard_pin_chk_flag())) {
            if (request.getCardChkParams().getCard_pin() == null
                    || request.getCardChkParams().getCard_pin().isEmpty()) {
                return ResponseData.fail("ERR1003", "卡密码不能为空", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
            if (!request.getCardChkParams().getCard_pin().equals(account.getCardPin())) {
                return ResponseData.fail("ERR1003", "校验身份信息失败", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
        }

        // 卡有效期检查(错误码ERR1003)
        if ("1".equals(request.getCardChkParams().getCard_expired_flag())) {
            if (request.getCardChkParams().getCard_expired_date() == null
                    || request.getCardChkParams().getCard_expired_date().isEmpty()) {
                return ResponseData.fail("ERR1003", "卡有效期不能为空", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
            if (!request.getCardChkParams().getCard_expired_date().equals(account.getCardExpiredDate())) {
                return ResponseData.fail("ERR1003", "校验身份信息失败", null, cardNo, account.getUsableAmount(),
                        account.getOverAmount());
            }
        }

        // 卡限额检查(错误码ERR1008)
        if (account.getDailyLimit().compareTo(transferAmount) < 0) {
            return ResponseData.fail("ERR1008", "超出单日交易限额", null, cardNo, account.getUsableAmount(),
                    account.getOverAmount());
        }

        // 计算手续费
        fee = calculateFee(request, account, transferAmount);

        // 余额检查
        BigDecimal totalDeduction = transferAmount.add(fee);
        if ("0".equals(request.getOver_flag()) && account.getUsableAmount().compareTo(totalDeduction) < 0) {
            return ResponseData.fail("ERR1001", "余额不足", null, cardNo, account.getUsableAmount(),
                    account.getOverAmount());
        }

        // 更新账户余额
        if (account.getUsableAmount().compareTo(totalDeduction) < 0) {
            BigDecimal deficit = totalDeduction.subtract(account.getUsableAmount());
            account.setUsableAmount(BigDecimal.ZERO);
            account.setOverAmount(account.getOverAmount().add(deficit));
        } else {
            account.setUsableAmount(account.getUsableAmount().subtract(totalDeduction));
        }

        // 更新账户信息
        creditCardAccountMapper.updateAccountBalance(account);
        log.info("After card account update - Card: {}, Usable: {}, Over: {}", account.getCardNo(),
                account.getUsableAmount(), account.getOverAmount());

        // 更新对方信用卡账户余额
        CreditCardAccount counterAccount = creditCardAccountMapper.findByCardNo(counterAccountNo);
        if (counterAccount != null) {
            counterAccount.setUsableAmount(counterAccount.getUsableAmount().add(transferAmount));
            creditCardAccountMapper.updateAccountBalance(counterAccount);
            log.info("After counter account update - Account: {}, Balance: {}", counterAccount.getCardNo(),
                    counterAccount.getUsableAmount());
        } else {
            log.warn("Counter account not found: {}", counterAccountNo);
        }

        // 构建成功响应数据
        SubField2 subField2 = SubField2.builder()
                .sub_field1(100)
                .sub_field2(true)
                .build();

        return ResponseData.success(
                "0",
                "交易成功",
                UUID.randomUUID().toString().replace("-", "").substring(0, 20),
                account.getCardNo(),
                account.getUsableAmount(),
                account.getOverAmount(),
                subField2);
    }

    private BigDecimal calculateFee(CreditCardTransferRequest request, CreditCardAccount account,
            BigDecimal transferAmount) {
        BigDecimal fee = BigDecimal.ZERO;

        // 异地交易手续费
        if ("1".equals(request.getChk_local_flag())
                && !request.getInfoParams().getTrx_zoneno().equals(account.getTrxZoneno())) {
            if ("1".equals(request.getFee_type())) {
                BigDecimal fixedFee = new BigDecimal("5");
                BigDecimal percentFee = transferAmount.multiply(BigDecimal.valueOf(0.01));
                fee = fee.add(fixedFee.max(percentFee));
            }
        }

        // 外币交易手续费
        if ("1".equals(request.getTransfer_currency_type())) {
            BigDecimal forexFee = transferAmount.multiply(BigDecimal.valueOf(0.015));
            fee = fee.add(forexFee);
        }

        return fee;
    }
}
