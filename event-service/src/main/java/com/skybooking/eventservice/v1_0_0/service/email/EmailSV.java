package com.skybooking.eventservice.v1_0_0.service.email;

import com.skybooking.eventservice.v1_0_0.ui.model.request.email.*;
import org.springframework.stereotype.Service;

@Service
public interface EmailSV {

    void send(SendRQ sendRQ);

    void topUp(SkyPointTopUpRQ skyPointTopUpRQ);

    void earning(SkyPointEarnedRQ skyPointEarnedRQ);

    void redeem(SkyPointRedeemRQ skyPointRedeemRQ);

    void refund(SkyPointRefundRQ skyPointRefundRQ);
}
