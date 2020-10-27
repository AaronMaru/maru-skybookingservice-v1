package com.skybooking.paymentservice.v1_0_0.service.implement;

import com.skybooking.paymentservice.v1_0_0.client.flight.action.FlightAction;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightPaymentFailureRQ;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentNQ;
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
public class IPay88IP {


    @Autowired
    private Payments payments;

    @Autowired
    private PaymentNQ paymentNQ;

    @Autowired
    private FlightAction flightAction;


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

        if (!(request.containsKey("Signature")
                && request.containsKey("Status")
                && request.containsKey("Amount")
                && request.containsKey("RefNo")
                && request.containsKey("PaymentId"))) {

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

    protected void paymentFail(Map<String, Object> request) {

        var bookingCode = request.get("RefNo").toString();
        var card = new CardInfo();
        var payment = new FlightPaymentFailureRQ();

        if (request.containsKey("CCNo")) {
            card = CardUtility.getCardInfo(request.get("CCNo").toString());
        }

        var paymentType = paymentNQ.getPaymentMethod(paymentNQ.getPaymentCode(bookingCode).getPaymentCode());

        payment.setStatus(0);
        payment.setBookingCode(bookingCode);
        payment.setCardNumber(card.getNumber());
        payment.setHolderName(request.containsKey("CCName") ? request.get("CCName").toString() : "");
        payment.setOrderId(bookingCode);
        payment.setAmount(new BigDecimal(request.get("Amount").toString()));
        payment.setCurrency(request.get("Currency").toString());
        payment.setTransId(request.containsKey("TransId") ? request.get("TransId").toString() : "");
        payment.setAuthCode(request.containsKey("AuthCode") ? request.get("AuthCode").toString() : "");
        payment.setIpay88Status(0);
        payment.setSignature(request.get("Signature").toString());
        payment.setIpay88PaymentId(request.get("PaymentId").toString());
        payment.setBankName(request.containsKey("S_bankname") ? request.get("S_bankname").toString().replace("+", " ") : "");
        payment.setPaymentCode(paymentType.getCode());
        payment.setCardType(card.getType());
        payment.setMethod(paymentType.getMethod());

        flightAction.paymentFail(payment);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate payment url for IPAY88
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    protected UrlPaymentRS getRequestUrlPoint(PaymentPointRQ paymentRQ) {
        return payments.getPaymentUrlPoint(paymentRQ, "ipay88/form");
    }
}
