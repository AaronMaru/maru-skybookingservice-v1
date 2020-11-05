package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PriceInfo {

    private BigDecimal cost = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal taxFeeAmount = BigDecimal.ZERO;
    private BigDecimal vatAmount = BigDecimal.ZERO;
    private BigDecimal accommodationTaxAmount = BigDecimal.ZERO;
    private BigDecimal totalRoomCharges = BigDecimal.ZERO;
    private BigDecimal totalExtraBedCharges = BigDecimal.ZERO;
    private BigDecimal totalAmountBeforeDiscount = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal discountPercentage = BigDecimal.ZERO;
    private BigDecimal commissionAmount = BigDecimal.ZERO;
    private BigDecimal markupPercentage = BigDecimal.ZERO;
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private BigDecimal paymentFeeAmount = BigDecimal.ZERO;
    private BigDecimal paidAmount = BigDecimal.ZERO;

}
