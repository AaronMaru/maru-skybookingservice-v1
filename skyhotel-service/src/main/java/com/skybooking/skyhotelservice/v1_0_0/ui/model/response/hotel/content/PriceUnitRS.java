package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.CancellationPolicy;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Discount;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Tax;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PriceUnitRS {

    @JsonIgnore
    private BigDecimal netAmount = BigDecimal.ZERO;
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal amountAfterDiscount = BigDecimal.ZERO;

    private BigDecimal discountPercentage = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @JsonIgnore
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private String currency = CurrencyConstant.USD.CODE;
    @JsonIgnore
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @JsonIgnore
    private List<Tax> taxes;

    @JsonIgnore
    private List<CancellationPolicy> cancellations;

    @JsonIgnore
    private List<Discount> discounts;

}
