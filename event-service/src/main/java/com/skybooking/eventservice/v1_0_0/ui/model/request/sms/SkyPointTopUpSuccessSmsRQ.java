package com.skybooking.eventservice.v1_0_0.ui.model.request.sms;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SkyPointTopUpSuccessSmsRQ {
    @NotNull(message = "Amount is missing.")
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal amount;

    @NotNull(message = "Phone is missing.")
    private String phone;

    @NotNull(message = "Full name is missing.")
    private String fullName;

    @NotNull(message = "Phone is missing.")
    @DecimalMin(value = "0.00", inclusive = false, message = "Amount is invalid.")
    private BigDecimal earnAmount;

    @NotNull(message = "TransactionId is missing.")
    private String transactionId;

    @NotNull(message = "TransactionDate is missing.")
    private String transactionDate;

    private String email;
}
