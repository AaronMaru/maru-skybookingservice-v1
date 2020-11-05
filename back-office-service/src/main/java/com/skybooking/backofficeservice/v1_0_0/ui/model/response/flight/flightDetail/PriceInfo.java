package com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.flightDetail;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class PriceInfo {
    private BigDecimal totalAmount = BigDecimal.ZERO;// amount not include markup
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private BigDecimal markupPercentage;
    private BigDecimal markupPaymentAmount;
    private BigDecimal paymentFee;
    private BigDecimal discountAmount;
    private BigDecimal paidAmount;
    private String currencyCode;
    private BigDecimal commissionAmount;
}
