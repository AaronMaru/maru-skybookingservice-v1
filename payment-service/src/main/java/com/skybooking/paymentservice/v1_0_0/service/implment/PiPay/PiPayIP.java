package com.skybooking.paymentservice.v1_0_0.service.implment.PiPay;

import com.skybooking.paymentservice.v1_0_0.service.interfaces.PiPay.PiPaySV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.General;
import com.skybooking.paymentservice.v1_0_0.util.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PiPayIP implements PiPaySV {


    @Autowired
    public Payments payments;


    @Autowired
    public General general;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate payment url for PIPAY
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    public UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ) {

        paymentRQ.setToken(general.paymentEncode(paymentRQ));
        paymentRQ.setProviderType("pipay");

        var url_token = general.getBaseUrl() + "/pipay/form?token=" + general.tokenEncodeBase64(payments.addUrlToken(paymentRQ).getToken());

        UrlPaymentRS urlPaymentRS = new UrlPaymentRS();
        urlPaymentRS.setUrlPayment(url_token);

        return urlPaymentRS;

    }



}
