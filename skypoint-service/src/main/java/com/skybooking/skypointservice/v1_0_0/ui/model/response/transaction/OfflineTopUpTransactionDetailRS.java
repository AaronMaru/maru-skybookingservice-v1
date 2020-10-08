package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicCompanyAccountInfoRS;
import lombok.Data;

@Data
public class OfflineTopUpTransactionDetailRS {
    private BasicCompanyAccountInfoRS basicAccountInfo;
    private AccountInfo accountInfo;
    private TopUpInfo topUpInfo;
}
