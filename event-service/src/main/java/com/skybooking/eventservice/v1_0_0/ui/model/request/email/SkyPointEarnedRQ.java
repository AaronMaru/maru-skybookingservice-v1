package com.skybooking.eventservice.v1_0_0.ui.model.request.email;

import com.skybooking.core.validators.annotations.IsEmail;
import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SkyPointEarnedRQ {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotNull
    private String transactionCode;

    @IsEmail
    private String email;

    @NotNull
    private String fullName;

    @IsIn(contains = {"FLIGHT", "HOTEL"}, message = "TransactionFor is invalid.")
    private String transactionFor;

}
