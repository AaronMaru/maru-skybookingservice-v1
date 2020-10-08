package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.OfflineTopUpTransactionDetailTO;
import lombok.Data;

import java.util.List;

@Data
public class SearchOfflineTopUpTransactionRS {
    List<OfflineTopUpTransactionDetailTO> offlineTopUpTransactionList;
}
