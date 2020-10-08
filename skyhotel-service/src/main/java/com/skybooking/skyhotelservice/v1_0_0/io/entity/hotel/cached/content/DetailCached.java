package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Discount;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Tax;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DetailCached implements Serializable {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal totalNet = BigDecimal.ZERO;
    private BigDecimal totalTaxesAmount = BigDecimal.ZERO;
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private BigDecimal markupPercentage = BigDecimal.ZERO;
    private String currency;
}
