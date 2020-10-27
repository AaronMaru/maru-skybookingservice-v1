package com.skybooking.skypointservice.v1_0_0.ui.model.response.history;

import lombok.Data;

import java.util.List;

@Data
public class TransactionHistoryRS {
    List<TransactionHistory> transactionHistoryList;
}
