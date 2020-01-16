package com.skybooking.paymentservice.v1_0_0.ui.controller;

import com.skybooking.paymentservice.v1_0_0.util.General;
import com.skybooking.paymentservice.v1_0_0.util.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PaymentFormController {

    @Autowired
    public Payments payments;

    @Autowired
    public General general;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request Payment Form PIPAY
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/pipay/form")
    public String pipayForm(@RequestParam Map<String, String> request, Model model) {

        var dataToken = payments.upadatePayment(request);
        if (dataToken.getRender() == 1) {
            return "error";
        }

        payments.updateUrlToken(dataToken.getId());
        payments.pipayPayload(payments.getPaymentInfo(dataToken), model);
        return "pipay/form";

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request Payment Form For IPAY88
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/ipay88/form")
    public String ipay88Form(@RequestParam Map<String, String> request, Model model) {

        var dataToken = payments.upadatePayment(request);
        if (dataToken.getRender() == 1) {
            return "error";
        }

        payments.updateUrlToken(payments.upadatePayment(request).getId());
        payments.ipay88Payload(payments.getPaymentInfo(dataToken), model);
        return "ipay88/form";

    }


}
