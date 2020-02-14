package com.skybooking.paymentservice.v1_0_0.ui.controller;

import com.skybooking.paymentservice.v1_0_0.client.flight.action.FlightAction;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.util.integration.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PaymentFormController {

    @Autowired
    private Payments payments;

    @Autowired
    private FlightAction flightAction;

    @Autowired
    private ProviderSV providerSV;

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
        payments.pipayPayload(payments.getPaymentInfo(dataToken, flightAction.getMandatoryData(dataToken.getBookingCode())), model);

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
        payments.ipay88Payload(payments.getPaymentInfo(dataToken, flightAction.getMandatoryData(dataToken.getBookingCode())), model);
        return "ipay88/form";

    }




    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get response from IPay88 and check it's succeed or fail. If it's succeed, it is going to issue air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/ipay88/response", consumes = {MediaType.ALL_VALUE})
    public String ipay88Response(@RequestParam Map<String, Object> request) {
        return "redirect:" + providerSV.getIpay88Response(request);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get pipay response success and continue issue air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @GetMapping("/pipay/success")
    public String pipaySucceed(@RequestParam Map<String, Object> request) {
        return "redirect:" + providerSV.getPipaySucceedResponse(request);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get pipay response fail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @GetMapping("/pipay/fail")
    public String pipayFail(@RequestParam Map<String, Object> request) {
        return "redirect:" + providerSV.getPipayFailResponse(request);
    }

}
