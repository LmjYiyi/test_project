package com.loxie.tested_project.model;

import java.math.BigDecimal;
import javax.validation.constraints.*;
import javax.validation.Valid;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardTransferResponse {
    @NotBlank
    private String return_code;

    @NotBlank
    private String return_msg;

    @NotBlank
    private String trx_serno;

    @NotBlank
    @Size(max = 19)
    private String card_no;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal usable_amount;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal over_amount;

    @Valid
    private ResponseData data;
}
