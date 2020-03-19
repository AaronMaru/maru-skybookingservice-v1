package com.skybooking.paymentservice.v1_0_0.service.implement;

import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.integration.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipayIP {


    @Autowired
    private Payments payments;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate payment url for PIPAY
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    protected UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ) {
        return payments.getPaymentUrl(paymentRQ, "pipay/form");
    }

}
