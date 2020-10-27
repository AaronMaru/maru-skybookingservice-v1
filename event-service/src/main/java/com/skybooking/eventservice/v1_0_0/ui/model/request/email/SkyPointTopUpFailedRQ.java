package com.skybooking.eventservice.v1_0_0.ui.model.request.email;

import com.skybooking.core.validators.annotations.IsEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SkyPointTopUpFailedRQ {
    @NotNull
    private BigDecimal amount;

    @NotNull
    private String fullName = "";

    @NotNull(message = "The email code can not be null")
    @IsEmail
    private String email;
}
