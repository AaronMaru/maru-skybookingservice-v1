package com.skybooking.paymentservice.v1_0_0.service.implement;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.constant.PaymentCodeConstant;
import com.skybooking.paymentservice.constant.ProductTypeConstant;
import com.skybooking.paymentservice.v1_0_0.client.flight.action.FlightAction;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightMandatoryDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightPaymentSucceedRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightTicketIssuedRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightPaymentSucceedRS;
import com.skybooking.paymentservice.v1_0_0.client.pipay.action.PipayAction;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentMethodTO;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentNQ;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProviderIP implements ProviderSV {

    @Autowired
    private IPay88IP iPay88IP;

    @Autowired
    private PipayIP piPayIP;

    @Autowired
    private PipayAction pipayAction;

    @Autowired
    private FlightAction flightAction;

    @Autowired
    private PaymentNQ paymentNQ;

    @Autowired
    private AppConfig appConfig;

    @Override
    public UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ) {

        if (paymentRQ.getProductType().equals(ProductTypeConstant.FLIGHT)) {

            PaymentMethodTO paymentMethodTO = paymentNQ.getPaymentMethod(paymentRQ.getPaymentCode());
            FlightMandatoryDataRQ mandatoryDataRQ = new FlightMandatoryDataRQ(
                    paymentRQ.getBookingCode(),
                    paymentRQ.getPaymentCode(),
                    paymentMethodTO.getPercentage(),
                    paymentMethodTO.getPercentageBase()
            );

            var mandatoryData = flightAction.updateDiscountPaymentMethod(mandatoryDataRQ);
            if (mandatoryData.getAmount().equals(BigDecimal.ZERO)) {
                return new UrlPaymentRS();
            }

            if (paymentRQ.getPaymentCode().equals(PaymentCodeConstant.PIPAY)) {
                return piPayIP.getRequestUrl(paymentRQ);
            }

            return iPay88IP.getRequestUrl(paymentRQ);
        }

        return new UrlPaymentRS();
    }



    @Override
    public List<PaymentMethodRS> getPaymentMethods() {

        List<PaymentMethodRS> paymentMethodRS = new ArrayList<>();
        List<PaymentMethodTO> paymentMethodTOS = paymentNQ.getListPaymentMethods();

        paymentMethodTOS.forEach(item -> {
            paymentMethodRS.add(new PaymentMethodRS(item.getType(), item.getCode(), item.getMethod(), item.getPercentage()));
        });

        return paymentMethodRS;
    }



    @Override
    public String getIpay88Response(Map<String, Object> request) {

        var bookingCode = request.get("RefNo").toString();
        if (request.get("Status").equals(0)) {
            return appConfig.getPaymentFailPage() + "?bookingCode=" + bookingCode;
        }

        /**
         * IPay88 verification
         */
        FlightMandatoryDataRS mandatoryData = flightAction.getMandatoryData(bookingCode);
        if (!iPay88IP.verifyPayment(request, mandatoryData.getAmount())) {
            return appConfig.getPaymentFailPage() + "?bookingCode=" + bookingCode;
        }

        FlightPaymentSucceedRQ paymentSucceedRQ = new FlightPaymentSucceedRQ();
        if (request.containsKey("S_bankname")) {
            paymentSucceedRQ.setBankName(request.get("S_bankname").toString().replace("+", " "));
        }

        paymentSucceedRQ.setCardNumber(request.containsKey("CCNo") ? request.get("CCNo").toString() : "");
        paymentSucceedRQ.setCardNumber(request.containsKey("CCName") ? request.get("CCName").toString() : "");
        paymentSucceedRQ.setIpay88Status(1);
        paymentSucceedRQ.setStatus(1);
        paymentSucceedRQ.setOrderId(request.get("RefNo").toString());
        paymentSucceedRQ.setBookingCode(request.get("RefNo").toString());
        paymentSucceedRQ.setAmount(mandatoryData.getAmount());
        paymentSucceedRQ.setCurrency(request.get("Currency").toString());
        paymentSucceedRQ.setTransId(request.containsKey("TransId") ? request.get("TransId").toString() : "");
        paymentSucceedRQ.setAuthCode(request.containsKey("AuthCode") ? request.get("AuthCode").toString() : "");
        paymentSucceedRQ.setSignature(request.containsKey("Signature") ? request.get("Signature").toString() : "");
        paymentSucceedRQ.setIpay88PaymentId(request.get("PaymentId").toString());
        // TODO
        paymentSucceedRQ.setPaymentCode("MS");
        paymentSucceedRQ.setCardType("");
        paymentSucceedRQ.setMethod("");

        FlightPaymentSucceedRS flightPaymentSucceedRS = flightAction.updateFlightPaymentSucceed(paymentSucceedRQ);
        if (flightPaymentSucceedRS.getStatus() != 1) {
            return appConfig.getPaymentFailPage() + "?bookingCode=" + bookingCode;
        }

        var ticketRQ = new FlightTicketIssuedRQ();
        ticketRQ.setBookingCode(bookingCode);
        var ticketIssued = flightAction.issuedAirTicket(ticketRQ);
        if (ticketIssued.getStatus().equals("Complete")) {
            return appConfig.getTicketSucceedPage() + "?bookingCode=" + bookingCode;
        }

        return appConfig.getTicketFailPage() + "?bookingCode=" + bookingCode;
    }



    @Override
    public String getPipaySucceedResponse(Map<String, Object> request) {

        /**
         * PiPay verification
         */
        var bookingCode = request.get("orderID").toString();
        FlightMandatoryDataRS mandatoryData = flightAction.getMandatoryData(bookingCode);
        if (!pipayAction.verify(request, mandatoryData.getAmount())) {
            return "redirect:" + appConfig.getPaymentFailPage() + "?bookingCode=" + bookingCode;
        }

        FlightPaymentSucceedRQ paymentSucceedRQ = new FlightPaymentSucceedRQ();
        paymentSucceedRQ.setAmount(mandatoryData.getAmount());
        paymentSucceedRQ.setPipiyStatus("0000");
        paymentSucceedRQ.setIpay88Status(0);
        paymentSucceedRQ.setStatus(1);
        paymentSucceedRQ.setDigest(request.get("digest").toString());
        paymentSucceedRQ.setOrderId(request.get("orderID").toString());
        paymentSucceedRQ.setBookingCode(request.get("orderID").toString());
        paymentSucceedRQ.setTransId(request.get("digest").toString());
        paymentSucceedRQ.setProcessorId(request.get("processorID").toString());
        paymentSucceedRQ.setBankName("Pi Pay");
        paymentSucceedRQ.setMethod("Pi Pay");
        paymentSucceedRQ.setPaymentCode(PaymentCodeConstant.PIPAY);
        paymentSucceedRQ.setCurrency("USD");

        /**
         * Update payment succeed for booking
         */
        var flightPaymentSucceedRS = flightAction.updateFlightPaymentSucceed(paymentSucceedRQ);
        if (flightPaymentSucceedRS.getStatus() != 1) {
            return "redirect:" + appConfig.getPaymentFailPage() + "?bookingCode=" + bookingCode;
        }

        /**
         * Issue air ticket
         */
        var ticketRQ = new FlightTicketIssuedRQ();
        ticketRQ.setBookingCode(bookingCode);
        var ticketIssued = flightAction.issuedAirTicket(ticketRQ);
        if (ticketIssued.getStatus().equals("Complete")) {
            return "redirect:" + appConfig.getTicketSucceedPage() + "?bookingCode=" + bookingCode;
        }

        return appConfig.getTicketFailPage() + "?bookingCode=" + bookingCode;
    }



    @Override
    public String getPipayFailResponse(Map<String, Object> request) {
        return appConfig.getPaymentFailPage() + "?bookingCode=" + request.get("orderID").toString();
    }

}
