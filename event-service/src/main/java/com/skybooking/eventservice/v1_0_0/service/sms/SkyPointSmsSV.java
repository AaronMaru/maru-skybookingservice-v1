package com.skybooking.eventservice.v1_0_0.service.sms;

import com.skybooking.eventservice.v1_0_0.ui.model.request.sms.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import org.springframework.stereotype.Service;

@Service
public interface SkyPointSmsSV {
    S3UploadRS sendTopUpSms(SkyPointTopUpSuccessSmsRQ skyPointTopUpSuccessSmsRQ);

    void sendTopUpFailed(SkyPointTopUpFailedSmsRQ skyPointTopUpFailedSmsRQ);

    void sendEarnedSms(SkyPointEarnSmsRQ skyPointEarnSmsRQ);

    void sendRedeemSms(SkyPointRedeemSmsRQ skyPointRedeemSmsRQ);

    void sendRefundSms(SkyPointRefundSmsRQ skyPointRefundSmsRQ);

    void upgradeLevel(SkyPointUpgradeLevelSmsRQ skyPointUpgradeLevelSmsRQ);
}

