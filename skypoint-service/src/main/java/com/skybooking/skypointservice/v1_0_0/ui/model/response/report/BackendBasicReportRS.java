package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BackendBasicReportRS {
    private BigDecimal topUp;
    private BigDecimal earning;
    private BigDecimal earningExtra;
    private BigDecimal savedPoint;
    private BigDecimal withdrawal;
}
