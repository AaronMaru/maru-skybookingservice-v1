package com.skybooking.paymentservice.v1_0_0.ui.controller;

import com.skybooking.paymentservice.v1_0_0.service.interfaces.IPay88.IPay88SV;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.PiPay.PiPaySV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.IPay88RQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.interfaces.PaymentCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("v1.0.0")
public class PaymentController {

    @Autowired
    public IPay88SV iPay88SV;

    @Autowired
    public PiPaySV piPaySV;


    @PostMapping("/request")
    public UrlPaymentRS request(@RequestBody PaymentRQ paymentRQ) {

        /**
         * Check for PiPay
         */
        if (paymentRQ.getPaymentCode().equals(PaymentCode.PIPAY)) {
            return piPaySV.getRequestUrl(paymentRQ);
        }

        return iPay88SV.getRequestUrl(paymentRQ);

    }


    @PostMapping(value = "/ipay88/response", consumes = {MediaType.ALL_VALUE})
    public String ipay88Response(@RequestParam Map<String, String> requestBody ) {

        System.out.println(requestBody);
        return "BB IPay88";

    }


    @PostMapping(value = "/ipay88/backend", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE} )
    public String ipay88Backend(@RequestBody IPay88RQ iPay88RQ) {

        System.out.println(iPay88RQ);
        return "RECEIVEOK";

    }

}
