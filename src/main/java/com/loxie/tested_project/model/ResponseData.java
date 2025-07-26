package com.loxie.tested_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    @JsonProperty("return_code")
    private String returnCode;
    @JsonProperty("return_msg")
    private String returnMsg;
    @JsonProperty("trx_serno")
    private String trxSerno;
    @JsonProperty("card_no")
    private String cardNo;
    @JsonProperty("usable_amount")
    private BigDecimal usableAmount;
    @JsonProperty("over_amount")
    private BigDecimal overAmount;
    private SubField2 data;

    public static ResponseData success(String returnCode, String returnMsg, String trxSerno, String cardNo,
            BigDecimal usableAmount, BigDecimal overAmount, SubField2 data) {
        return ResponseData.builder()
                .returnCode(returnCode)
                .returnMsg(returnMsg)
                .trxSerno(trxSerno)
                .cardNo(cardNo)
                .usableAmount(usableAmount)
                .overAmount(overAmount)
                .data(data)
                .build();
    }

    public static ResponseData fail(String returnCode, String returnMsg, String trxSerno, String cardNo,
            BigDecimal usableAmount, BigDecimal overAmount) {
        return ResponseData.builder()
                .returnCode(returnCode)
                .returnMsg(returnMsg)
                .trxSerno(trxSerno)
                .cardNo(cardNo)
                .usableAmount(usableAmount)
                .overAmount(overAmount)
                .data(null)
                .build();
    }
}
