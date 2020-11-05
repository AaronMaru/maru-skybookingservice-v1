package com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.hotelDetail;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceInfo {
  private BigDecimal amount = BigDecimal.ZERO; // amount not include markup
  private BigDecimal markupAmount = BigDecimal.ZERO;
  private Integer markupPercentage;
  private BigDecimal markupPayment = BigDecimal.ZERO;
  private BigDecimal totalDiscount;
  private BigDecimal commissionAmount;
  private BigDecimal totalAmount;
  private BigDecimal discountAmount;
}
