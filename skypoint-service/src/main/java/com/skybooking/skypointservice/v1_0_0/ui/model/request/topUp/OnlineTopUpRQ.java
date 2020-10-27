package com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp;

import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
public class OnlineTopUpRQ {
    @Digits(integer = 10, fraction = 0, message = "decimal_not_allow")
    private BigDecimal amount;
    private String paymentMethodCode;
    private String remark;
}
