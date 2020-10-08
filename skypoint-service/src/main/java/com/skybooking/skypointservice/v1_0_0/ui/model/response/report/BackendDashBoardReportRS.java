package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.OfflineTopUpTransactionDetailTO;
import lombok.Data;

import java.util.List;

@Data
public class BackendDashBoardReportRS {
    private AccountSummary accountSummary;
    private List<OfflineTopUpTransactionDetailTO> recentOfflineTopUpList;
    private List<TopBalance> topBalanceList;
    private List<TopEarning> topEarningList;
}
