package com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CheckOfflineItineraryRS {
    private BigDecimal amount;
    private BigDecimal commission;
}
