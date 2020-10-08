package com.skybooking.skypointservice.v1_0_0.ui.controller.payment;

import com.skybooking.skypointservice.v1_0_0.service.payment.PaymentSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.payment.PaymentRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("v1.0.0/payment")
public class PaymentController {

    @Autowired
    private PaymentSV paymentSV;

    @RequestMapping(value = "redeem/check", method = RequestMethod.POST)
    public StructureRS redeemCheck(HttpServletRequest httpServletRequest, @RequestBody PaymentRQ paymentRQ) {
        return paymentSV.redeemCheck(httpServletRequest, paymentRQ);
    }

    @RequestMapping(value = "redeem/confirm", method = RequestMethod.POST)
    public StructureRS redeemConfirm(HttpServletRequest httpServletRequest, @RequestBody PaymentRQ paymentRQ) {
        return paymentSV.redeemConfirm(httpServletRequest, paymentRQ);
    }

}
