package com.skybooking.skypointservice.v1_0_0.service.payment;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.payment.PaymentRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface PaymentSV {

    StructureRS earning(PaymentRQ paymentRQ);

    StructureRS redeemCheck(PaymentRQ paymentRQ);

    StructureRS redeemConfirm(PaymentRQ paymentRQ);
}
