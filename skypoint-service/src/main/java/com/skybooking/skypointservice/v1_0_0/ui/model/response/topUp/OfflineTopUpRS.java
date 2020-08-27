package com.skybooking.skypointservice.v1_0_0.ui.model.response.topUp;

import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.OfflineTopUpTransactionDetailTO;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.AccountInfo;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.TopUpInfo;
import lombok.Data;

import java.util.List;

@Data
public class OfflineTopUpRS {
    private BasicAccountInfoRS basicAccountInfoRS;
    private AccountInfo accountInfo;
    private TopUpInfo topUpInfo;
    private List<OfflineTopUpTransactionDetailTO> recentOfflineTopUpTransactionList;
}
