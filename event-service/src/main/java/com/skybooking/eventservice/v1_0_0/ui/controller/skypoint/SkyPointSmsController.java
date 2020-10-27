package com.skybooking.eventservice.v1_0_0.ui.controller.skypoint;

import com.skybooking.eventservice.v1_0_0.service.sms.SkyPointSmsSV;
import com.skybooking.eventservice.v1_0_0.ui.model.request.sms.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("v1.0.0/sms")
public class SkyPointSmsController {

    @Autowired
    private SkyPointSmsSV skyPointSmsSV;

    @PostMapping("no-auth/top-up/success")
    public S3UploadRS topUpSkyPoint(@Valid @RequestBody SkyPointTopUpSuccessSmsRQ skyPointTopUpSuccessSmsRQ) {
        return skyPointSmsSV.sendTopUpSms(skyPointTopUpSuccessSmsRQ);
    }

    @PostMapping("no-auth/top-up/failed")
    public void topUpSkyPointFailed(@Valid @RequestBody SkyPointTopUpFailedSmsRQ skyPointTopUpFailedSmsRQ) {
        skyPointSmsSV.sendTopUpFailed(skyPointTopUpFailedSmsRQ);
    }

    @PostMapping("earned")
    public void earningPoint(@Valid @RequestBody SkyPointEarnSmsRQ skyPointEarnSmsRQ) {
        skyPointSmsSV.sendEarnedSms(skyPointEarnSmsRQ);
    }

    @PostMapping("redeem")
    public void redeemPoint(@Valid @RequestBody SkyPointRedeemSmsRQ skyPointRedeemSmsRQ) {
        skyPointSmsSV.sendRedeemSms(skyPointRedeemSmsRQ);
    }

    @PostMapping("refund")
    public void refundPoint(@Valid @RequestBody SkyPointRefundSmsRQ skyPointRefundSmsRQ) {
        skyPointSmsSV.sendRefundSms(skyPointRefundSmsRQ);
    }
    
}
