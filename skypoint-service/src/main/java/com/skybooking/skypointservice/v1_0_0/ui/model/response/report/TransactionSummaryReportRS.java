package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.TransactionSummaryReportTO;
import lombok.Data;

import java.util.List;

@Data
public class TransactionSummaryReportRS {
    private TransactionSummaryReportTO transactionSummaryReportInfo;
    private List<TransactionSummaryReport> transactionSummaryReportList;
}
