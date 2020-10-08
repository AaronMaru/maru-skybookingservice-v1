package com.skybooking.skypointservice.v1_0_0.ui.model.response.topUp;

import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicCompanyAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.AccountInfo;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.TopUpInfo;
import lombok.Data;

@Data
public class OfflineTopUpRS {
    private BasicCompanyAccountInfoRS basicCompanyAccountInfoRS;
    private AccountInfo accountInfo;
    private TopUpInfo topUpInfo;
}
