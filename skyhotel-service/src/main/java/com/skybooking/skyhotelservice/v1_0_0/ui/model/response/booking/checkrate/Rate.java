package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Rate {

    private String key;
    private String boardCode;
    @JsonIgnore
    private BigDecimal unitNet = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal totalNet = BigDecimal.ZERO;
    private BigDecimal unitAmount = BigDecimal.ZERO;
    private BigDecimal subTotalAmount = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @JsonIgnore
    private BigDecimal markupAmount = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal markupPercentage = BigDecimal.ZERO;
//    @JsonIgnore
    private BigDecimal commissionAmount = BigDecimal.ZERO;

    private BigDecimal previousTotalAmount = BigDecimal.ZERO;
    private Boolean isChanged = Boolean.FALSE;
    private String currencyCode;

    private Integer totalRoom = 0;
    private Integer totalNight = 0;

    private BigDecimal totalDiscount = BigDecimal.ZERO;
    @JsonIgnore
    private List<Discount> discounts;
    private BigDecimal totalTax = BigDecimal.ZERO;
        @JsonIgnore
    private BigDecimal unitTax = BigDecimal.ZERO;
    private List<Tax> tax;

    private List<CancellationPolicy> cancellationPolicy;

}
