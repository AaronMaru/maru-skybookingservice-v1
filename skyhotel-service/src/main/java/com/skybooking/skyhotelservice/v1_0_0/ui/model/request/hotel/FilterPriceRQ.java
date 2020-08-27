package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FilterPriceRQ {

    private BigDecimal min;
    private BigDecimal max;

}
