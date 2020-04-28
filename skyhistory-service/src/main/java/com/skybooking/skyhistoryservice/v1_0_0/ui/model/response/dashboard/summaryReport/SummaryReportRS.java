package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard.summaryReport;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class SummaryReportRS {

    private String title;
    private String color;
    private BigInteger quantity = BigInteger.ZERO;
    private BigDecimal amount = BigDecimal.ZERO;
    private Double percentage = 0.0;
    private String predictionStatus = "";
    private List<StaffReportListRS> listStaff = new ArrayList<>();

}
