package com.loxie.tested_project.model;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreditCardTransferRequest {
    @NotBlank
    @Size(max = 1)
    private String chk_name_flag;

    @Size(max = 60)
    private String person_name;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal transfer_amount;

    @Size(max = 1)
    private String fee_type;

    @NotBlank
    @Size(max = 1)
    private String transfer_currency_type;

    @NotNull
    private Integer transfer_currency;

    @Size(max = 1)
    private String chk_local_flag;

    @Size(max = 1)
    private String over_flag;

    @NotNull
    @Valid
    private CounterParams counterParams;

    @NotNull
    @Valid
    private CardChkParams cardChkParams;

    @NotNull
    @Valid
    private InfoParams infoParams;

    @Valid
    private CerChkParams cerChkParams;
}
