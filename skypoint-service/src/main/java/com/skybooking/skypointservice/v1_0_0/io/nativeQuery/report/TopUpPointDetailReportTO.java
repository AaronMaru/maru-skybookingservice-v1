package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TopUpPointDetailReportTO {
    protected String transactionCode;
    protected String userCode;
    protected String userName;
    protected String type;
    protected String transactionTypeName;
    protected BigDecimal amount;
    protected BigDecimal earning;
    protected String proceedBy;
    protected Date createdAt;
}
