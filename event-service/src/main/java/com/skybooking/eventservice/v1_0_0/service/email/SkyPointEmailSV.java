package com.skybooking.eventservice.v1_0_0.service.email;

import com.skybooking.eventservice.v1_0_0.ui.model.request.email.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import org.springframework.stereotype.Service;


public interface SkyPointEmailSV {

    void send(SendRQ sendRQ);

    S3UploadRS topUp(SkyPointTopUpRQ skyPointTopUpRQ);

    void earning(SkyPointEarnedRQ skyPointEarnedRQ);

    void redeem(SkyPointRedeemRQ skyPointRedeemRQ);

    void refund(SkyPointRefundRQ skyPointRefundRQ);
}
