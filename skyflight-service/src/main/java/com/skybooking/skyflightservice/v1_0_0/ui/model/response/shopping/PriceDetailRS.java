package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PriceDetailRS implements Serializable {

    private String id;
    private BigDecimal total;
    private BigDecimal totalAvg;
    private String currency;
    private String baseCurrency;
    private BigDecimal baseCurrencyTotal;
    private BigDecimal baseCurrencyTotalAvg;
    private List<PriceRS> details = new ArrayList<>();
}
