package com.skybooking.skypointservice.v1_0_0.ui.controller.payment;

import com.skybooking.skypointservice.v1_0_0.service.payment.PaymentSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.payment.PaymentRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("v1.0.0/payment")
public class PaymentController {

    @Autowired
    private PaymentSV paymentSV;

    @PostMapping("redeem/check")
    public StructureRS redeemCheck(@RequestBody PaymentRQ paymentRQ) {
        return paymentSV.redeemCheck(paymentRQ);
    }

    @PostMapping("redeem/confirm")
    public StructureRS redeemConfirm(@RequestBody PaymentRQ paymentRQ) {
        return paymentSV.redeemConfirm(paymentRQ);
    }

}
