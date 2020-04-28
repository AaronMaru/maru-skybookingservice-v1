package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Price implements Serializable {

    BigDecimal total = BigDecimal.ZERO;
    BigDecimal totalAverage = BigDecimal.ZERO;
    BigDecimal totalCommission = BigDecimal.ZERO;
    BigDecimal grandTotalAverage = BigDecimal.ZERO;
    BigDecimal grandTotal = BigDecimal.ZERO;
    String currency;

}
