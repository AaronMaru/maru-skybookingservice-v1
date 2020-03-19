package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PriceDetail implements Serializable {

    private String id;
    private String currency;
    private BigDecimal total;
    private BigDecimal totalAvg;
    private BigDecimal totalCommissionAmount;
    private String baseCurrency;
    private BigDecimal baseCurrencyTotal;
    private BigDecimal baseCurrencyTotalAvg;
    private List<Price> details = new ArrayList<>();

}
