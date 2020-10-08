package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Discount;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Tax;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DetailRS {
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @JsonIgnore
    private BigDecimal totalNet = BigDecimal.ZERO;

    private BigDecimal totalTaxesAmount = BigDecimal.ZERO;

    @JsonIgnore
    private BigDecimal markupAmount = BigDecimal.ZERO;

    @JsonIgnore
    private BigDecimal markupPercentage = BigDecimal.ZERO;

    private String currency;
}
