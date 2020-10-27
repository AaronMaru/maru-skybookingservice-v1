package com.skybooking.skypointservice.v1_0_0.ui.model.request.payment;

import com.skybooking.core.validators.annotations.IsAlpha;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentRQ {
    @Digits(integer = 10, fraction = 3)
    private BigDecimal amount;
    private String transactionFor;
    private String referenceCode;
    private String paymentMethod;
}
