package com.skybooking.eventservice.v1_0_0.service.email;

import com.skybooking.eventservice.v1_0_0.ui.model.request.email.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;


public interface SkyPointEmailSV {

    void send(SendRQ sendRQ);

    S3UploadRS topUpSuccess (SkyPointTopUpSuccessRQ skyPointTopUpSuccessRQ);

    void topUpFailed (SkyPointTopUpFailedRQ skyPointTopUpFailedRQ);

    void earning(SkyPointEarnedRQ skyPointEarnedRQ);

    void redeem(SkyPointRedeemRQ skyPointRedeemRQ);

    void refund(SkyPointRefundRQ skyPointRefundRQ);

    void upgradeLevel(SkyPointUpgradeLevelRQ skyPointUpgradeLevelRQ);
}
