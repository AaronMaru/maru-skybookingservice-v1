package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpgradedLevelSummaryReportTO {
    protected BigDecimal level1;
    protected BigDecimal level2;
    protected BigDecimal level3;
    protected BigDecimal level4;
}
