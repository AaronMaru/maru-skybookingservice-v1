package com.skybooking.eventservice.v1_0_0.ui.controller;

import com.skybooking.eventservice.v1_0_0.service.email.EmailSV;
import com.skybooking.eventservice.v1_0_0.ui.model.request.email.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/email")
public class EmailController {

    @Autowired
    private EmailSV emailSV;

    @PostMapping
    public void send(@Validated @RequestBody SendRQ sendRQ) {
        emailSV.send(sendRQ);
    }

    @PostMapping("no-auth/top-up")
    public void topUpSkyPoint(@Validated @RequestBody SkyPointTopUpRQ skyPointTopUpRQ) {
        emailSV.topUp(skyPointTopUpRQ);
    }

    @PostMapping("earned")
    public void earningPoint(@Validated @RequestBody SkyPointEarnedRQ skyPointEarnedRQ) {
        emailSV.earning(skyPointEarnedRQ);
    }

    @PostMapping("redeem")
    public void redeemPoint(@Validated @RequestBody SkyPointRedeemRQ skyPointRedeemRQ) {
        emailSV.redeem(skyPointRedeemRQ);
    }

    @PostMapping("refund")
    public void refundPoint(@Validated @RequestBody SkyPointRefundRQ skyPointRefundRQ) {
        emailSV.refund(skyPointRefundRQ);
    }
}
