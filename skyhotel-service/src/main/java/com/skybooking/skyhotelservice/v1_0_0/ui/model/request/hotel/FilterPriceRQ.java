package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
public class FilterPriceRQ {

    @Min(0)
    private BigDecimal min;

    @Min(0)
    private BigDecimal max;

}
