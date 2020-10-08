package com.skybooking.eventservice.v1_0_0.ui.model.request.email;

import com.skybooking.core.validators.annotations.IsEmail;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SkyPointTopUpRQ {

    @NotNull
    @DecimalMin(value = "1.0", inclusive = false)
    private BigDecimal amount;

    @IsEmail
    private String email;

    @NotNull
    private String fullName;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal earnAmount;

    private String phone;

    @NotNull
    private String transactionId;

    @NotNull
    private String transactionDate;

}