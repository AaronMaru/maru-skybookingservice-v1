package com.skybooking.skypointservice.v1_0_0.ui.model.response.history;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history.TransactionHistoryTO;
import lombok.Data;

import java.util.List;

@Data
public class FilterTransactionHistoryRS {
    private List<TransactionHistoryTO> transactionHistoryList;
}

