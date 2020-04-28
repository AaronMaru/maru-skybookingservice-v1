package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceRS implements Serializable {

    BigDecimal total = BigDecimal.ZERO;
    BigDecimal totalAverage = BigDecimal.ZERO;
    BigDecimal grandTotalAverage = BigDecimal.ZERO;
    BigDecimal grandTotal = BigDecimal.ZERO;
    String currency;

}
