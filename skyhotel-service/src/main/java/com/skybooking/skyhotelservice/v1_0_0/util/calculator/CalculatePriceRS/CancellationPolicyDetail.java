package com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.CancellationPolicy;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Tax;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CancellationPolicyDetail {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<CancellationPolicy> cancellationPolicies;
}
