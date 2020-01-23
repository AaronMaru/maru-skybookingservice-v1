package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceRS implements Serializable {

    private String type;
    private BigDecimal tax = BigDecimal.ZERO;
    private BigDecimal baseFare = BigDecimal.ZERO;
    private String currency;
    private int quantity;

}
