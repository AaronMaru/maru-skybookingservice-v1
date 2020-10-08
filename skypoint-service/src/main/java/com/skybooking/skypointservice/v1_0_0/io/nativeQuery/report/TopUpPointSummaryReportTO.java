package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpPointSummaryReportTO {
    protected BigDecimal skyUser;
    protected BigDecimal skyOwnerOnline;
    protected BigDecimal skyOwnerOffline;
    protected BigDecimal earnedSkyUser;
    protected BigDecimal earnedSkyOwnerOnline;
    protected BigDecimal earnedSkyOwnerOffline;
}
