package com.skybooking.paymentservice.v1_0_0.service.implement;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.constant.BookingStatusConstant;
import com.skybooking.paymentservice.constant.PaymentCodeConstant;
import com.skybooking.paymentservice.constant.PaymentStatusConstant;
import com.skybooking.paymentservice.constant.ProductTypeConstant;
import com.skybooking.paymentservice.v1_0_0.client.flight.action.FlightAction;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightMandatoryDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightPaymentSucceedRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.pipay.action.PipayAction;
import com.skybooking.paymentservice.v1_0_0.client.stakeholder.action.SkyHistoryAction;
import com.skybooking.paymentservice.v1_0_0.io.enitity.redis.BookingLanguageCached;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentMethodTO;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentNQ;
import com.skybooking.paymentservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.paymentservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PriceDetailRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.paymentservice.v1_0_0.util.classse.CardInfo;
import com.skybooking.paymentservice.v1_0_0.util.generator.CardUtility;
import com.skybooking.paymentservice.v1_0_0.util.generator.GeneralUtility;
import com.skybooking.paymentservice.v1_0_0.util.header.HeaderBean;
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
    private SkyHistoryAction skyHistoryAction;

    @Autowired
    private PaymentNQ paymentNQ;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private ActivityLoggingBean activityLog;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    BookingLanguageRedisRP bookingLanguageRedisRP;

    @Override
    public UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ) {

        var booking = bookingRP.getBooking(paymentRQ.getBookingCode());
        booking.setStatus(BookingStatusConstant.PAYMENT_SELECTED);
        bookingRP.save(booking);
        activityLog.activities(ActivityLoggingBean.Action.INDEX_PAYMNET_METHOD_SELECT, activityLog.getUser(), booking);

        //Save Language to redis for email template
        BookingLanguageCached bookingLanguageCached = new BookingLanguageCached();
        bookingLanguageCached.setBookingCode(booking.getBookingCode());
        bookingLanguageCached.setLanguage(headerBean.getLocalization());
        bookingLanguageRedisRP.save(bookingLanguageCached);

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
    public List<PaymentMethodRS> getPaymentMethods(String bookingCode) {

        List<PaymentMethodRS> paymentMethodRS = new ArrayList<>();
        List<PaymentMethodTO> paymentMethodTOS = paymentNQ.getListPaymentMethods();
        var booking = bookingRP.getBooking(bookingCode);

        paymentMethodTOS.forEach(item -> {

            var totalAmount = booking.getTotalAmount().add(booking.getMarkupAmount().add(booking.getMarkupPayAmount()));
            var commission = booking.getCommissionAmount();

            totalAmount = totalAmount.subtract(commission);
            var discount = GeneralUtility.trimAmount(totalAmount.multiply(item.getPercentage().divide(new BigDecimal(100))));
            discount = discount.add(commission);

            var paidAmount = totalAmount.subtract(discount);

            paymentMethodRS.add(
                new PaymentMethodRS(
                    item.getType(),
                    item.getCode(),
                    item.getMethod(),
                    item.getPercentage(),
                    appConfig.getImagePathPaymentMethod() + item.getCode() + ".png",
                    new PriceDetailRS(totalAmount, discount, paidAmount)
                )
            );
        });

        return paymentMethodRS;
    }


    @Override
    public String getIpay88Response(Map<String, Object> request) {

        var bookingCode = request.get("RefNo").toString();

        if (request.get("Status").equals("0")) {

            iPay88IP.paymentFail(request);

            return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;
        }

        FlightMandatoryDataRS mandatoryData = flightAction.getMandatoryData(bookingCode);

        /**
         * IPay88 verification
         */
        if (!iPay88IP.verifyPayment(request, mandatoryData.getAmount())) {
            return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;
        }

        /**
         * TODO: log info verify succeed
         */

        FlightPaymentSucceedRQ paymentSucceedRQ = new FlightPaymentSucceedRQ();
        if (request.containsKey("S_bankname")) {
            paymentSucceedRQ.setBankName(request.get("S_bankname").toString().replace("+", " "));
        }

        var card = new CardInfo();
        if (request.containsKey("CCNo")) {
            card = CardUtility.getCardInfo(request.get("CCNo").toString());
        }
        var paymentType = paymentNQ.getPaymentMethod(paymentNQ.getPaymentCode(bookingCode).getPaymentCode());

        paymentSucceedRQ.setCardNumber(card.getNumber());
        paymentSucceedRQ.setHolderName(request.containsKey("CCName") ? request.get("CCName").toString() : "");
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
        paymentSucceedRQ.setPaymentCode(paymentType.getCode());
        paymentSucceedRQ.setCardType(card.getType());
        paymentSucceedRQ.setMethod(paymentType.getMethod());
        paymentSucceedRQ.setEmail(mandatoryData.getEmail());
        paymentSucceedRQ.setSkyuserId(mandatoryData.getSkyuserId());
        paymentSucceedRQ.setCompanyId(mandatoryData.getCompanyId());
        flightAction.updateFlightPaymentSucceed(paymentSucceedRQ);

        return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_SUCCESS;
    }


    @Override
    public String getPipaySucceedResponse(Map<String, Object> request) {

        /**
         * PiPay verification
         */
        var bookingCode = request.get("orderID").toString();
        FlightMandatoryDataRS mandatoryData = flightAction.getMandatoryData(bookingCode);

        if (!pipayAction.verify(request, mandatoryData.getAmount())) {
            return "redirect:" + appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;
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
        flightAction.updateFlightPaymentSucceed(paymentSucceedRQ);
        return "redirect:" + appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_SUCCESS;

    }


    @Override
    public String getPipayFailResponse(Map<String, Object> request) {

        FlightMandatoryDataRS mandatoryData = flightAction.getMandatoryData(request.get("orderID").toString());
        return appConfig.getPaymentPage() + "?bookingCode=" + mandatoryData.getBookingCode() + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;

    }

}
