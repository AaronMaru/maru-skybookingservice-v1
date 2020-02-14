package com.skybooking.skyflightservice.v1_0_0.util.classes.booking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceInfo {

    private BigDecimal grossAmount;
    private BigDecimal finalAmount;
    private BigDecimal discountPaymentMethodPercentage;
    private BigDecimal discountPaymentMethodAmount;
    private BigDecimal markupPaymentMethodPercentage;
    private BigDecimal markupPaymentMethodAmount;
    private BigDecimal paymentMethodFeePercentage;
    private BigDecimal paymentMethodFeeAmount;

}
