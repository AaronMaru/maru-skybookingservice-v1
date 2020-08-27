package com.skybooking.paymentservice.v1_0_0.service.implement;

import com.skybooking.paymentservice.v1_0_0.client.flight.action.FlightAction;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightPaymentFailureRQ;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentNQ;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.PipaySV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentHotelRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentPointRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.classse.CardInfo;
import com.skybooking.paymentservice.v1_0_0.util.generator.CardUtility;
import com.skybooking.paymentservice.v1_0_0.util.integration.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PipayIP implements PipaySV {

    @Autowired
    private PaymentNQ paymentNQ;

    @Autowired
    private Payments payments;

    @Autowired
    private FlightAction flightAction;

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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate payment url for PIPAY
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    protected UrlPaymentRS getRequestUrlPoint(PaymentPointRQ paymentRQ) {
        return payments.getPaymentUrlPoint(paymentRQ, "pipay/form");
    }

    public void paymentFail(Map<String, Object> request) {

        var bookingCode = request.get("orderID").toString();
        var card = new CardInfo();
        var payment = new FlightPaymentFailureRQ();

        var paymentType = paymentNQ.getPaymentMethod(paymentNQ.getPaymentCode(bookingCode).getPaymentCode());

        payment.setStatus(0);
        payment.setPipayStatus("0");
        payment.setBookingCode(bookingCode);
        payment.setCardNumber(card.getNumber());
        payment.setOrderId(bookingCode);
        payment.setTransId(request.containsKey("transID") ? request.get("transID").toString() : "");
        payment.setIpay88Status(0);
        payment.setPaymentCode(paymentType.getCode());
        payment.setCardType(card.getType());
        payment.setMethod(paymentType.getMethod());

        flightAction.paymentFail(payment);

    }
}
