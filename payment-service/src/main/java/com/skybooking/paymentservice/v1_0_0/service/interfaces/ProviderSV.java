package com.skybooking.paymentservice.v1_0_0.service.interfaces;

import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentPointRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodAvailableRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProviderSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get request URL
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get available payment methods
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    List<PaymentMethodRS> getPaymentMethods(String bookingCode);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get ipay88 response and check succeed or fail. If it's succeed, it is going to issue air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    String getIpay88Response(Map<String, Object> request);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get pipay response success and continue issue air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    String getPipaySucceedResponse(Map<String, Object> request);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get pipay response fail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    String getPipayFailResponse(Map<String, Object> request);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get available payment methods available
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    List<PaymentMethodAvailableRS> getPaymentMethodsAvailable();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get available payment methods available by code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    PaymentMethodAvailableRS getPaymentMethodsAvailableByCode(String code);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get request URL
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    UrlPaymentRS getPointRequestUrl(PaymentPointRQ paymentRQ);
}
