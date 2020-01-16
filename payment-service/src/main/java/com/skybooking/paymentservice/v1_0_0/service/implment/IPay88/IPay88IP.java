package com.skybooking.paymentservice.v1_0_0.service.implment.IPay88;

import com.skybooking.paymentservice.v1_0_0.service.interfaces.IPay88.IPay88SV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.General;
import com.skybooking.paymentservice.v1_0_0.util.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPay88IP implements IPay88SV {


    @Autowired
    public Payments payments;


    @Autowired
    public General general;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate payment url for IPAY88
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    public UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ) {

        paymentRQ.setToken(general.paymentEncode(paymentRQ));
        paymentRQ.setProviderType("ipay88");

        var url_token = general.getBaseUrl() + "/ipay88/form?token=" + general.tokenEncodeBase64(payments.addUrlToken(paymentRQ).getToken());

        UrlPaymentRS urlPaymentRS = new UrlPaymentRS();
        urlPaymentRS.setUrlPayment(url_token);

        return urlPaymentRS;

    }

}
