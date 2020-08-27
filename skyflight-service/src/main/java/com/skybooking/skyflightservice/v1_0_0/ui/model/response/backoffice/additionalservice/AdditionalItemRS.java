package com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.additionalservice;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdditionalItemRS {
    private BigDecimal fee;
    private String description;
}
