package com.loxie.tested_project.model;

import javax.validation.constraints.*;
import lombok.Data;

@Data
public class CerChkParams {
    @NotBlank
    @Size(max = 1)
    private String cer_chk;

    @Size(max = 2)
    private String cer_type;

    @Size(max = 20)
    private String cer_no;
}
