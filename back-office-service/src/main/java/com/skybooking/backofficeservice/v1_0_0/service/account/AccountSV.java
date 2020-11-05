package com.skybooking.backofficeservice.v1_0_0.service.account;

import com.skybooking.backofficeservice.v1_0_0.client.model.response.account.AccountStructureServiceRS;

public interface AccountSV {

    /**
     * Get Profile (Skyuser, Skyowner) detail
     * @return AccountStructureRS
     */
    AccountStructureServiceRS profileDetail();
}
