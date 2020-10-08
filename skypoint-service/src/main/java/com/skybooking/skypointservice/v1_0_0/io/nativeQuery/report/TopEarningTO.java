package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopEarningTO {
    protected String userCode;
    protected String levelCode;
    protected String levelName;
    protected BigDecimal savedPoint;
}
