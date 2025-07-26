package com.loxie.tested_project.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CardChkParams {
    @NotBlank
    @Size(max = 19)
    private String card_no;

    @Size(max = 1)
    private String card_pin_chk_flag;

    @Size(max = 6)
    private String card_pin;

    @Size(max = 1)
    private String card_expired_flag;

    @Size(max = 6)
    @Pattern(regexp = "\\d{6}")
    private String card_expired_date;

}
