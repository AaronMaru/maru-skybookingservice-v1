package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.TransactionSummaryReportTO;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.account.BalanceInfo;
import lombok.Data;

import java.util.List;

@Data
public class BackendAccountInfoReportRS {
    private BalanceInfo balanceInfo;
    private List<TransactionSummaryReportTO> transactionSummaryReportList;
}
