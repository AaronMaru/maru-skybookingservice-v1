package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.price;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.CancellationPolicy;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Discount;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Tax;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PriceUnitCached implements Serializable {
    private BigDecimal netAmount = BigDecimal.ZERO;
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal amountAfterDiscount = BigDecimal.ZERO;
    private BigDecimal discountPercentage = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private BigDecimal taxAmount = BigDecimal.ZERO;
    private List<Tax> taxes;
    private List<Discount> discounts;
    private List<CancellationPolicy> cancellations;
    private String currency = CurrencyConstant.USD.CODE;

}
