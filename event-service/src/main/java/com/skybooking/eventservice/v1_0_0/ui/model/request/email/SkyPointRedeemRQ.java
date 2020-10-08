package com.skybooking.eventservice.v1_0_0.ui.model.request.email;

import com.skybooking.core.validators.annotations.IsEmail;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SkyPointRedeemRQ {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @IsEmail
    private String email;

    @NotNull
    private String transactionCode;

    @NotNull
    private String fullName;

}
