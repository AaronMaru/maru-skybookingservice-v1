package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.PendingOfflineTopUpTransactionTO;
import lombok.Data;

import java.util.List;

@Data
public class PendingOfflineTopUpTransactionRS {
    List<PendingOfflineTopUpTransactionTO> pendingOfflineTransactionList;
}
