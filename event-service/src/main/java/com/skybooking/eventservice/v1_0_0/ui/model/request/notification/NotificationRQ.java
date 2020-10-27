package com.skybooking.eventservice.v1_0_0.ui.model.request.notification;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class NotificationRQ {

    @IsNotEmpty
    @NotNull
    private String transactionCode;

    @IsNotEmpty
    @IsIn(contains = {"EARNED_POINT", "REDEEM_POINT", "REFUND_POINT"})
    private String type;

    private String script;

    @IsIn(contains = {"FLIGHT", "HOTEL"}, message = "TransactionFor is invalid.")
    private String transactionFor;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
}
