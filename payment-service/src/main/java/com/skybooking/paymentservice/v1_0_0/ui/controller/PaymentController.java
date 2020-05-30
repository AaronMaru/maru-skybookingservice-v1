package com.skybooking.paymentservice.v1_0_0.ui.controller;

import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.IPay88RQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.BaseRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1.0.0")
public class PaymentController {

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
    public ResponseEntity<BaseRS<UrlPaymentRS>> request(@Valid @RequestBody PaymentRQ paymentRQ) {

        return new ResponseEntity<>(new BaseRS<>(HttpStatus.OK.value(), "", providerSV.getRequestUrl(paymentRQ)), HttpStatus.OK);

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get available payment method
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping("/methods/{bookingCode}")
    public ResponseEntity<BaseRS<List<PaymentMethodRS>>> getPaymentMethods(@PathVariable String bookingCode) {
        return new ResponseEntity<>(new BaseRS<>(HttpStatus.OK.value(), "", providerSV.getPaymentMethods(bookingCode)), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify background process with ipay88 between payment service with ipay88 server
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param iPay88RQ
     * @return
     */
    @PostMapping(value = "/ipay88/backend", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String ipay88Backend(@RequestBody IPay88RQ iPay88RQ) {

        System.out.println(iPay88RQ);

        return "RECEIVEOK";

    }

}
