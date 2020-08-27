package com.skybooking.skypointservice.v1_0_0.ui.model.response.account;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.AccountTransactionTO;
import lombok.Data;

import java.util.List;

@Data
public class BackendAccountInfoRS {
    private BalanceInfo balanceInfo;
    private List<AccountTransactionTO> accountRecentTransactionList;
    private BackendAccountTransactionSummary accountTransactionSummary;
}
