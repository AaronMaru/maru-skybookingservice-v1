package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Room {

    private String code;
    @JsonIgnore
    private BigDecimal cost = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private BigDecimal totalTaxFees = BigDecimal.ZERO;
    private BigDecimal totalDiscountAmount = BigDecimal.ZERO;
    private BigDecimal previousTotalAmount = BigDecimal.ZERO;
    private Boolean isChanged = Boolean.FALSE;
//    @JsonIgnore
    private BigDecimal commissionAmount = BigDecimal.ZERO;
    private String currencyCode ;
    private List<Rate> rates;

}
