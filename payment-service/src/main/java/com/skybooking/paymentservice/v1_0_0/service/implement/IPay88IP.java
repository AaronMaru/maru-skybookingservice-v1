package com.skybooking.paymentservice.v1_0_0.service.implement;

import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.integration.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class IPay88IP {


    @Autowired
    private Payments payments;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate payment url for IPAY88
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    protected UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ) {
        return payments.getPaymentUrl(paymentRQ, "ipay88/form");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * IPay88 verification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @param amount
     * @return
     */
    protected Boolean verifyPayment(Map<String, Object> request, BigDecimal amount) {

        if ( !(request.containsKey("Signature")
                && request.containsKey("Status")
                && request.containsKey("Amount")
                && request.containsKey("RefNo")
                && request.containsKey("PaymentId")) ) {

            return false;
        }

        var paymentId = request.get("PaymentId").toString();
        var refNo = request.get("RefNo").toString();
        var amountIpay88 = request.get("Amount").toString();
        var status = request.get("Status").toString();
        var responseSignature = request.get("Signature").toString();
        var signature = payments.ipay88Signature(paymentId, refNo, amountIpay88, status);
        var replaceComma = amountIpay88.replace(",", "");
        var responseAmount = new BigDecimal(replaceComma);

        if (responseSignature.equals(signature) && responseAmount.equals(amount)) {
            return true;
        }

        return false;
    }

}
