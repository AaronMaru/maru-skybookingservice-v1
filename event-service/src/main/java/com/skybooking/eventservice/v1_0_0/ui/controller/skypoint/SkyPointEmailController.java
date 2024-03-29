package com.skybooking.eventservice.v1_0_0.ui.controller.skypoint;

import com.amazonaws.services.xray.model.Http;
import com.skybooking.eventservice.v1_0_0.service.email.SkyPointEmailSV;
import com.skybooking.eventservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.eventservice.v1_0_0.ui.model.request.email.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/email")
public class SkyPointEmailController extends BaseController {

    @Autowired
    private SkyPointEmailSV emailSV;

    @PostMapping
    public void send(@Valid @RequestBody SendRQ sendRQ) {
        emailSV.send(sendRQ);
    }

    @PostMapping("no-auth/top-up/success")
    public ResponseEntity<StructureRS> topUpSkyPointSuccess(@Valid @RequestBody SkyPointTopUpSuccessRQ skyPointTopUpSuccessRQ) {

        S3UploadRS s3UploadRS = emailSV.topUpSuccess(skyPointTopUpSuccessRQ);

        return response(new StructureRS(HttpStatus.OK.value(),"is_skb_info", s3UploadRS));

    }

    @PostMapping("no-auth/top-up/failed")
    public ResponseEntity<StructureRS> topUpSkyPointFailed(@Valid @RequestBody SkyPointTopUpFailedRQ skyPointTopUpFailedRQ) {
        emailSV.topUpFailed(skyPointTopUpFailedRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("earned")
    public ResponseEntity<StructureRS> earningPoint(@Valid @RequestBody SkyPointEarnedRQ skyPointEarnedRQ) {
        emailSV.earning(skyPointEarnedRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("redeem")
    public ResponseEntity<StructureRS> redeemPoint(@Validated @RequestBody SkyPointRedeemRQ skyPointRedeemRQ) {
        emailSV.redeem(skyPointRedeemRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("refund")
    public ResponseEntity<StructureRS> refundPoint(@Validated @RequestBody SkyPointRefundRQ skyPointRefundRQ) {
        emailSV.refund(skyPointRefundRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("upgrade-level")
    public void upgradeLevel(@Validated @RequestBody SkyPointUpgradeLevelRQ skyPointUpgradeLevelRQ) {
        emailSV.upgradeLevel(skyPointUpgradeLevelRQ);
    }

}
