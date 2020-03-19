package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Price implements Serializable {

    private String type;
    private BigDecimal tax;
    private BigDecimal baseCurrencyTax;
    private BigDecimal baseFare;
    private BigDecimal baseCurrencyBaseFare;
    private BigDecimal commissionAmount;
    private BigDecimal commissionPercentage;
    private String currency;
    private String baseCurrency;
    private int quantity;

}
