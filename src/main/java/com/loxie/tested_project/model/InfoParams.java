package com.loxie.tested_project.model;

import javax.validation.constraints.*;
import lombok.Data;

@Data
public class InfoParams {
    @NotNull
    private Integer trx_zoneno;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String trx_workdate;

    @NotBlank
    @Pattern(regexp = "\\d{2}:\\d{2}:\\d{2}")
    private String trx_time;
}
