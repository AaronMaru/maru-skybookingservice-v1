package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundedPointSummaryReportTO {
    protected BigDecimal skyUser;
    protected BigDecimal skyOwner;
}
