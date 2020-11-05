package com.skybooking.eventservice.v1_0_0.ui.controller.skypoint;

import com.skybooking.eventservice.v1_0_0.service.sms.SkyPointSmsSV;
import com.skybooking.eventservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.eventservice.v1_0_0.ui.model.request.sms.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("v1.0.0/sms")
public class SkyPointSmsController extends BaseController {

    @Autowired
    private SkyPointSmsSV skyPointSmsSV;

    @PostMapping("no-auth/top-up/success")
    public ResponseEntity<StructureRS> topUpSkyPoint(@Valid @RequestBody SkyPointTopUpSuccessSmsRQ skyPointTopUpSuccessSmsRQ) {
        S3UploadRS s3UploadRS = skyPointSmsSV.sendTopUpSms(skyPointTopUpSuccessSmsRQ);
        return response(new StructureRS(HttpStatus.OK.value(),"is_skb_info", s3UploadRS));
    }

    @PostMapping("no-auth/top-up/failed")
    public ResponseEntity<StructureRS> topUpSkyPointFailed(@Valid @RequestBody SkyPointTopUpFailedSmsRQ skyPointTopUpFailedSmsRQ) {
        skyPointSmsSV.sendTopUpFailed(skyPointTopUpFailedSmsRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("earned")
    public ResponseEntity<StructureRS> earningPoint(@Valid @RequestBody SkyPointEarnSmsRQ skyPointEarnSmsRQ) {
        skyPointSmsSV.sendEarnedSms(skyPointEarnSmsRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("redeem")
    public ResponseEntity<StructureRS> redeemPoint(@Valid @RequestBody SkyPointRedeemSmsRQ skyPointRedeemSmsRQ) {
        skyPointSmsSV.sendRedeemSms(skyPointRedeemSmsRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));

    }

    @PostMapping("refund")
    public ResponseEntity<StructureRS> refundPoint(@Valid @RequestBody SkyPointRefundSmsRQ skyPointRefundSmsRQ) {
        skyPointSmsSV.sendRefundSms(skyPointRefundSmsRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("upgrade-level")
    public ResponseEntity<StructureRS> upgradeLevel(@Valid @RequestBody SkyPointUpgradeLevelSmsRQ skyPointUpgradeLevelSmsRQ) {
        skyPointSmsSV.upgradeLevel(skyPointUpgradeLevelSmsRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }
}
