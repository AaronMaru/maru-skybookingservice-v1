package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LayoverAirportRS extends LocationRS {

    private String currency;
    private BigDecimal price = BigDecimal.ZERO;

}
