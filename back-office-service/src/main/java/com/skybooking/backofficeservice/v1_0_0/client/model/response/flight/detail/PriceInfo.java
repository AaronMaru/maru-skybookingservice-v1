package com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.detail;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceInfo {
    private BigDecimal totalAmount = BigDecimal.ZERO;// amount not include markup
    private BigDecimal paidAmount;
    private String currencyCode;
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private BigDecimal markupPercentage;
    private BigDecimal markupPaymentAmount;
    private BigDecimal paymentFee;
    private BigDecimal discountAmount;
    private BigDecimal commissionAmount;
}
