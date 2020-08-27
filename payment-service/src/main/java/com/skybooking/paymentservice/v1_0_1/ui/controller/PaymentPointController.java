package com.skybooking.paymentservice.v1_0_1.ui.controller;

import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentPointRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.BaseRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.1")
public class PaymentPointController {
    @Autowired
    private ProviderSV providerSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request payment URL
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    @PostMapping("/request")
    public ResponseEntity<BaseRS<UrlPaymentRS>> request(@Valid @RequestBody PaymentPointRQ paymentRQ) {
        return new ResponseEntity<>(
                new BaseRS<>(HttpStatus.OK.value(), "", providerSV.getPointRequestUrl(paymentRQ)), HttpStatus.OK
        );
    }

}
