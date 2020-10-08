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
public class EarningController {

    @Autowired
    private PaymentSV paymentSV;

    @RequestMapping(value = "earning/check", method = RequestMethod.POST)
    public StructureRS earningCheck(HttpServletRequest httpServletRequest, @RequestBody PaymentRQ paymentRQ) {
        return paymentSV.earningCheck(httpServletRequest, paymentRQ);
    }

    @RequestMapping(value = "earning/confirm", method = RequestMethod.POST)
    public StructureRS earningConfirm(HttpServletRequest httpServletRequest, @RequestBody PaymentRQ paymentRQ) {
        return paymentSV.earningConfirm(httpServletRequest, paymentRQ);
    }

}
