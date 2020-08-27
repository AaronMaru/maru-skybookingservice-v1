package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceInfoRS {

    private BigDecimal costAmount;
    private BigDecimal paidAmount;
    private BigDecimal discountPaymentMethodPercentage;
    private BigDecimal discountPaymentMethodAmount;
    private BigDecimal markupPaymentMethodPercentage;
    private BigDecimal markupPaymentMethodAmount;
    private BigDecimal basePaymentPercentage;
    private BigDecimal basePaymentAmount;

}
