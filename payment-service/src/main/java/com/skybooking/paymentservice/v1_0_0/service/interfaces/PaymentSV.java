package com.skybooking.paymentservice.v1_0_0.service.interfaces;

import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;

public interface PaymentSV {

    UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ);
}
