package com.skybooking.skypointservice.v1_0_0.ui.model.response.topUp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PreTopUpRS {
    private BigDecimal topUpSkyPoint;
    private BigDecimal earnSkyPoint;
    private BigDecimal totalSkyPoint;
    private BigDecimal amountPayable;
}
