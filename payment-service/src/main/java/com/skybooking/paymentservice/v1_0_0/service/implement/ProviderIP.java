package com.skybooking.paymentservice.v1_0_0.service.implement;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.constant.*;
import com.skybooking.paymentservice.v1_0_0.client.flight.action.FlightAction;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightMandatoryDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.PaymentSucceedRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.hotel.action.HotelAction;
import com.skybooking.paymentservice.v1_0_0.client.hotel.ui.request.HotelMandatoryDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.hotel.ui.response.HotelMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.pipay.action.PipayAction;
import com.skybooking.paymentservice.v1_0_0.client.point.action.PointAction;
import com.skybooking.paymentservice.v1_0_0.client.point.ui.PointRQ;
import com.skybooking.paymentservice.v1_0_0.client.point.ui.PostOnlineTopUpRQ;
import com.skybooking.paymentservice.v1_0_0.io.enitity.booking.BookingEntity;
import com.skybooking.paymentservice.v1_0_0.io.enitity.booking.HotelBookingEntity;
import com.skybooking.paymentservice.v1_0_0.io.enitity.redis.BookingLanguageCached;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentMethodTO;
import com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod.PaymentNQ;
import com.skybooking.paymentservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.paymentservice.v1_0_0.io.repository.booking.HotelBookingRP;
import com.skybooking.paymentservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentPointRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodAvailableRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PriceDetailRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.paymentservice.v1_0_0.util.classse.CardInfo;
import com.skybooking.paymentservice.v1_0_0.util.encrypt.AESEncryptionDecryption;
import com.skybooking.paymentservice.v1_0_0.util.generator.CardUtility;
import com.skybooking.paymentservice.v1_0_0.util.generator.GeneralUtility;
import com.skybooking.paymentservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.paymentservice.v1_0_1.ui.model.response.StructureRS;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
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

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private HotelBookingRP hotelBookingRP;

    @Autowired
    private ActivityLoggingBean activityLog;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    BookingLanguageRedisRP bookingLanguageRedisRP;

    @Autowired
    private HotelAction hotelAction;

    @Autowired
    private GeneralUtility general;

    @Autowired
    private PointAction pointAction;

    @Autowired
    private Environment environment;

    @Override
    public UrlPaymentRS getRequestUrl(PaymentRQ paymentRQ) {

        // Save Language to redis for email template
        BookingLanguageCached bookingLanguageCached = new BookingLanguageCached();
        bookingLanguageCached.setBookingCode(paymentRQ.getBookingCode());
        bookingLanguageCached.setLanguage(headerBean.getLocalization());
        bookingLanguageRedisRP.save(bookingLanguageCached);

        if (paymentRQ.getProductType().equals(ProductTypeConstant.FLIGHT)) {
            return getFlightAction(paymentRQ);
        }

        if (paymentRQ.getProductType().equals(ProductTypeConstant.HOTEL)) {
            return getHotelAction(paymentRQ);
        }

        return new UrlPaymentRS();
    }

    public UrlPaymentRS getFlightAction(PaymentRQ paymentRQ) {

        var booking = bookingRP.getBooking(paymentRQ.getBookingCode());
        booking.setStatus(BookingStatusConstant.PAYMENT_SELECTED);
        bookingRP.save(booking);
        activityLog.activities(ActivityLoggingBean.Action.INDEX_PAYMNET_METHOD_SELECT, activityLog.getUser(), booking);

        PaymentMethodTO paymentMethodTO = paymentNQ.getPaymentMethod(paymentRQ.getPaymentCode());
        FlightMandatoryDataRQ mandatoryDataRQ = new FlightMandatoryDataRQ(
            paymentRQ.getBookingCode(),
            paymentRQ.getPaymentCode(),
            paymentMethodTO.getPercentage(),
            paymentMethodTO.getPercentageBase()
        );

        FlightMandatoryDataRS mandatoryData = flightAction.updateDiscountPaymentMethod(mandatoryDataRQ);
        if (mandatoryData.getAmount().equals(BigDecimal.ZERO)) {
            return new UrlPaymentRS();
        }

        if (paymentRQ.getPaymentCode().equals(PaymentCodeConstant.PIPAY)) {
            return piPayIP.getRequestUrl(paymentRQ);
        }

        if (paymentRQ.getPaymentCode().equals(PaymentCodeConstant.SKY_POINT)) {
            return this.redeemPointProcess(paymentRQ, mandatoryData, null);
        }

        return iPay88IP.getRequestUrl(paymentRQ);

    }

    public UrlPaymentRS getHotelAction(PaymentRQ paymentRQ) {

        general.updatePaymentBookingStatus(paymentRQ.getBookingCode(), HotelBookingStatusConstant.PAYMENT_SELECTED);

        if (paymentRQ.getProductType().equals(ProductTypeConstant.HOTEL)) {

            PaymentMethodTO paymentMethodTO = paymentNQ.getPaymentMethod(paymentRQ.getPaymentCode());
            HotelMandatoryDataRQ mandatoryDataRQ = new HotelMandatoryDataRQ(paymentRQ.getBookingCode(),
                paymentRQ.getPaymentCode(), paymentMethodTO.getPercentage(), paymentMethodTO.getPercentageBase());

            HotelMandatoryDataRS mandatoryData = hotelAction.updateDiscountPaymentMethod(mandatoryDataRQ);
            if (mandatoryData.getAmount().equals(BigDecimal.ZERO)) {
                return new UrlPaymentRS();
            }

            if (paymentRQ.getPaymentCode().equals(PaymentCodeConstant.PIPAY)) {
                return piPayIP.getRequestUrl(paymentRQ);
            }

            if (paymentRQ.getPaymentCode().equals(PaymentCodeConstant.SKY_POINT)) {

                return this.redeemPointProcess(paymentRQ, null, mandatoryData);
            }

            return iPay88IP.getRequestUrl(paymentRQ);
        }

        return new UrlPaymentRS();
    }

    @Override
    public List<PaymentMethodRS> getPaymentMethods(String bookingCode) {

        List<PaymentMethodRS> paymentMethodRS = new ArrayList<>();
        List<PaymentMethodTO> paymentMethodTOS = paymentNQ.getListPaymentMethods();

        var booking = new BookingEntity();
        var hotelBooking = new HotelBookingEntity();

        if (bookingCode.contains("SBFT")) {
            booking = bookingRP.getBooking(bookingCode);
        }
        if (bookingCode.contains("SBHT")) {
            hotelBooking = hotelBookingRP.getBooking(bookingCode);
        }

        BookingEntity finalBooking = booking;
        HotelBookingEntity finalHotelBooking = hotelBooking;

        paymentMethodTOS.forEach(item -> {

            var totalAmount = BigDecimal.ZERO;
            var commission = BigDecimal.ZERO;

            if (bookingCode.contains("SBFT")) {
                totalAmount = finalBooking.getTotalAmount()
                    .add(finalBooking.getMarkupAmount().add(finalBooking.getMarkupPayAmount()));
                commission = finalBooking.getCommissionAmount();
            }
            if (bookingCode.contains("SBHT")) {
                // To do
                totalAmount = finalHotelBooking.getTotalAmount();
                commission = finalHotelBooking.getCommisionAmount();
            }

            totalAmount = totalAmount.subtract(commission);
            var discount = GeneralUtility
                .trimAmount(totalAmount.multiply(item.getPercentage().divide(new BigDecimal(100))));
            discount = discount.add(commission);

            var paidAmount = totalAmount.subtract(discount);


            PriceDetailRS priceDetail = new PriceDetailRS(totalAmount, discount, paidAmount);

            if (item.getCode().equalsIgnoreCase("SP")) {
                priceDetail.setCurrency("PTS");
            }

            paymentMethodRS.add(new PaymentMethodRS(item.getType(), item.getCode(), item.getMethod(),
                item.getPercentage(), appConfig.getImagePathPaymentMethod() + item.getCode() + ".png",
                priceDetail));
        });

        return paymentMethodRS;
    }

    @Override
    public String getIpay88Response(Map<String, Object> request) {

        var bookingCode = request.get("RefNo").toString();

        if (bookingCode.contains("SBFT")) {

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
            PaymentSucceedRQ paymentSucceedRQ = paymentIpay88SucceedRQ(request, bookingCode, mandatoryData);
            flightAction.updateFlightPaymentSucceed(paymentSucceedRQ);

        } else if (bookingCode.contains("PTS")) {

            PostOnlineTopUpRQ postOnlineTopUpRQ = new PostOnlineTopUpRQ();
            postOnlineTopUpRQ.setTransactionCode(bookingCode);
            postOnlineTopUpRQ.setPaymentStatus("success");

            if (request.get("Status").equals("0")) {

                log.info("Payment Fail : {}", request);
                postOnlineTopUpRQ.setPaymentStatus("fail");
                return appConfig.getPointTopUp() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;
            }

            StructureRS structureRS = pointAction.paymentPointTopUpPost(postOnlineTopUpRQ);

            return appConfig.getPointTopUp() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_SUCCESS;

        } else if (bookingCode.contains("SBHT")) {
            if (request.get("Status").equals("0")) {
                iPay88IP.paymentFail(request);
                return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;
            }
            FlightMandatoryDataRS mandatoryData = hotelAction.getMandatoryData(bookingCode);
            if (!iPay88IP.verifyPayment(request, mandatoryData.getAmount())) {
                return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;
            }
            PaymentSucceedRQ paymentSucceedRQ = paymentIpay88SucceedRQ(request, bookingCode, mandatoryData);
            hotelAction.updateHotelPaymentSucceed(paymentSucceedRQ);

        }

        return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_SUCCESS;
    }

    private PaymentSucceedRQ paymentPointByFlightSucceedRQ(String bookingCode, FlightMandatoryDataRS mandatoryData) {

        PaymentSucceedRQ paymentSucceedRQ = new PaymentSucceedRQ();

        paymentSucceedRQ.setBankName("SkyPoint");
        paymentSucceedRQ.setStatus(1);
        paymentSucceedRQ.setBookingCode(bookingCode);
        paymentSucceedRQ.setPaymentCode("SP");
        paymentSucceedRQ.setMethod("POINT");
        paymentSucceedRQ.setAmount(mandatoryData.getAmount());
        paymentSucceedRQ.setSkyuserId(mandatoryData.getSkyuserId());
        paymentSucceedRQ.setEmail(mandatoryData.getEmail());
        paymentSucceedRQ.setCompanyId(mandatoryData.getCompanyId());
        paymentSucceedRQ.setCurrency("USD");

        return paymentSucceedRQ;
    }

    private PaymentSucceedRQ paymentPointByHotelSucceedRQ(String bookingCode, HotelMandatoryDataRS mandatoryData) {

        PaymentSucceedRQ paymentSucceedRQ = new PaymentSucceedRQ();

        paymentSucceedRQ.setBankName("SkyPoint");
        paymentSucceedRQ.setStatus(1);
        paymentSucceedRQ.setPaymentCode("SP");
        paymentSucceedRQ.setBookingCode(bookingCode);
        paymentSucceedRQ.setMethod("POINT");
        paymentSucceedRQ.setAmount(mandatoryData.getAmount());
        paymentSucceedRQ.setEmail(mandatoryData.getEmail());
        paymentSucceedRQ.setSkyuserId(mandatoryData.getSkyuserId());
        paymentSucceedRQ.setCompanyId(mandatoryData.getCompanyId());
        paymentSucceedRQ.setCurrency("USD");

        return paymentSucceedRQ;
    }

    private PaymentSucceedRQ paymentIpay88SucceedRQ(Map<String, Object> request, String bookingCode,
                                                    FlightMandatoryDataRS mandatoryData) {

        PaymentSucceedRQ paymentSucceedRQ = new PaymentSucceedRQ();
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

        return paymentSucceedRQ;
    }

    @Override
    public String getPipaySucceedResponse(Map<String, Object> request) {

        /**
         * PiPay verification
         */
        String bookingCode = request.get("orderID").toString();
        String page = appConfig.getPaymentPage();

        if (bookingCode.contains("SBFT")) {

            FlightMandatoryDataRS mandatoryData = flightAction.getMandatoryData(bookingCode);
            if (!pipayAction.verify(request, mandatoryData.getAmount())) {
                return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status="
                    + PaymentStatusConstant.PAYMENT_FAIL;
            }
            PaymentSucceedRQ paymentSucceedRQ = pipaySucceedRS(request, mandatoryData);
            /**
             * Update payment succeed for booking
             */
            flightAction.updateFlightPaymentSucceed(paymentSucceedRQ);
        } else if (bookingCode.contains("SBHT")) {
            general.updatePaymentBookingStatus(bookingCode, HotelBookingStatusConstant.PAYMENT_PROCESSING);
            FlightMandatoryDataRS mandatoryData = hotelAction.getMandatoryData(bookingCode);
            if (!pipayAction.verify(request, mandatoryData.getAmount())) {
                return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status="
                    + PaymentStatusConstant.PAYMENT_FAIL;
            }
            PaymentSucceedRQ paymentSucceedRQ = pipaySucceedRS(request, mandatoryData);
            hotelAction.updateHotelPaymentSucceed(paymentSucceedRQ);
        } else if (bookingCode.contains("PTS")) {

            PostOnlineTopUpRQ postOnlineTopUpRQ = new PostOnlineTopUpRQ();
            postOnlineTopUpRQ.setPaymentStatus("success");
            postOnlineTopUpRQ.setTransactionCode(bookingCode);

            PointRQ pointRQ = new PointRQ();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", bookingCode);

            String initVector = environment.getProperty("encryption.iv");
            String key = environment.getProperty("encryption.key");

            try {
                pointRQ.setData(AESEncryptionDecryption.encrypt(jsonObject.toString(), key, initVector));
            } catch (Exception e) {
                e.printStackTrace();
            }

            StructureRS structureRS = pointAction.getMandatoryData(pointRQ);

            Map<String, Object> dataRS = structureRS.getData();

            if (!pipayAction.verify(request, new BigDecimal(dataRS.get("amount").toString()))) {

                postOnlineTopUpRQ.setPaymentStatus("fail");
                log.info("Payment Fail : {}", request);
                return appConfig.getPaymentPage() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_FAIL;
            }

            StructureRS potTopUpResponse = pointAction.paymentPointTopUpPost(postOnlineTopUpRQ);

            return appConfig.getPointTopUp() + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_SUCCESS;

        }

        return page + "?bookingCode=" + bookingCode + "&status=" + PaymentStatusConstant.PAYMENT_SUCCESS;

    }

    private PaymentSucceedRQ pipaySucceedRS(Map<String, Object> request, FlightMandatoryDataRS mandatoryData) {

        PaymentSucceedRQ paymentSucceedRQ = new PaymentSucceedRQ();
        paymentSucceedRQ.setAmount(mandatoryData.getAmount());
        paymentSucceedRQ.setPipayStatus("0000");
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
        paymentSucceedRQ.setEmail(mandatoryData.getEmail());
        paymentSucceedRQ.setSkyuserId(mandatoryData.getSkyuserId());
        paymentSucceedRQ.setCompanyId(mandatoryData.getCompanyId());

        return paymentSucceedRQ;

    }

    @Override
    public String getPipayFailResponse(Map<String, Object> request) {

        FlightMandatoryDataRS mandatoryData = new FlightMandatoryDataRS();

        if (request.get("orderID").toString().contains("SBFT")) {
            mandatoryData = flightAction.getMandatoryData(request.get("orderID").toString());
        }
        if (request.get("orderID").toString().contains("SBHT")) {
            mandatoryData = hotelAction.getMandatoryData(request.get("orderID").toString());
        }

        return appConfig.getPaymentPage() + "?bookingCode=" + mandatoryData.getBookingCode() + "&status="
            + PaymentStatusConstant.PAYMENT_FAIL;

    }

    @Override
    public List<PaymentMethodAvailableRS> getPaymentMethodsAvailable() {

        List<PaymentMethodAvailableRS> paymentMethodRS = new ArrayList<>();

        List<PaymentMethodTO> paymentMethodTOS = paymentNQ.getListPaymentMethods();

        paymentMethodTOS.forEach(item -> {
            paymentMethodRS.add(new PaymentMethodAvailableRS(item.getType(), item.getCode(), item.getMethod(),
                item.getPercentage(), appConfig.getImagePathPaymentMethod() + item.getCode() + ".png"));
        });

        return paymentMethodRS;

    }

    @Override
    public PaymentMethodAvailableRS getPaymentMethodsAvailableByCode(String code) {

        PaymentMethodAvailableRS paymentMethodRS = null;

        PaymentMethodTO paymentMethodTOS = paymentNQ.getPaymentMethod(code);

        if (paymentMethodTOS != null) {
            paymentMethodRS = new PaymentMethodAvailableRS();
            BeanUtils.copyProperties(paymentMethodTOS, paymentMethodRS);
            paymentMethodRS.setImageUrl(appConfig.getImagePathPaymentMethod() + paymentMethodTOS.getCode() + ".png");
        }

        return paymentMethodRS;

    }

    @Override
    public UrlPaymentRS getPointRequestUrl(PaymentPointRQ paymentRQ) {

        if (paymentRQ.getProductType().equals(ProductTypeConstant.FLIGHT)) {

            var booking = bookingRP.getBooking(paymentRQ.getBookingCode());
            booking.setStatus(BookingStatusConstant.PAYMENT_SELECTED);
            bookingRP.save(booking);
            activityLog.activities(ActivityLoggingBean.Action.INDEX_PAYMNET_METHOD_SELECT, activityLog.getUser(),
                booking);

            // Save Language to redis for email template
            BookingLanguageCached bookingLanguageCached = new BookingLanguageCached();
            bookingLanguageCached.setBookingCode(booking.getBookingCode());
            bookingLanguageCached.setLanguage(headerBean.getLocalization());
            bookingLanguageRedisRP.save(bookingLanguageCached);

            PaymentMethodTO paymentMethodTO = paymentNQ.getPaymentMethod(paymentRQ.getPaymentCode());
            FlightMandatoryDataRQ mandatoryDataRQ = new FlightMandatoryDataRQ(paymentRQ.getBookingCode(),
                paymentRQ.getPaymentCode(), paymentMethodTO.getPercentage(), paymentMethodTO.getPercentageBase());

            var mandatoryData = flightAction.updateDiscountPaymentMethod(mandatoryDataRQ);
            if (mandatoryData.getAmount().equals(BigDecimal.ZERO)) {
                return new UrlPaymentRS();
            }

            if (paymentRQ.getPaymentCode().equals(PaymentCodeConstant.PIPAY)) {
                return piPayIP.getRequestUrlPoint(paymentRQ);
            }

            return iPay88IP.getRequestUrlPoint(paymentRQ);
        } else if (paymentRQ.getProductType().equals(ProductTypeConstant.POINT)) {

            if (paymentRQ.getPaymentCode().equals(PaymentCodeConstant.PIPAY)) {
                return piPayIP.getRequestUrlPoint(paymentRQ);
            }

            return iPay88IP.getRequestUrlPoint(paymentRQ);
        }

        return new UrlPaymentRS();
    }

    private UrlPaymentRS redeemPointProcess(PaymentRQ paymentRQ, FlightMandatoryDataRS flightData, HotelMandatoryDataRS hotelData) {

        PointRQ pointRQ = new PointRQ();

        JSONObject jsonObject = new JSONObject();
        if (paymentRQ.getProductType().equals("FLIGHT")) {
            jsonObject.put("amount", flightData.getAmount());
        } else {
            jsonObject.put("amount", hotelData.getAmount());
        }

        jsonObject.put("transactionFor", paymentRQ.getProductType());
        jsonObject.put("referenceCode", paymentRQ.getBookingCode());

        String key = environment.getProperty("encryption.key");
        String initVector = environment.getProperty("encryption.iv");

        try {
            pointRQ.setData(AESEncryptionDecryption.encrypt(jsonObject.toString(), key, initVector));
        } catch (Exception e) {
            e.printStackTrace();
        }

        StructureRS structureRS = pointAction.payByRedeemPoint(pointRQ);
        UrlPaymentRS urlPaymentRS = new UrlPaymentRS();

        if (structureRS.getStatus() == HttpStatus.OK.value()) {

            urlPaymentRS.setUrlPayment(appConfig.getPaymentPage() + "?bookingCode=" + paymentRQ.getBookingCode()
                + "&status=" + PaymentStatusConstant.PAYMENT_SUCCESS);


            if (paymentRQ.getProductType().equals(ProductTypeConstant.FLIGHT)) {
                PaymentSucceedRQ paymentSucceedRQ = paymentPointByFlightSucceedRQ(paymentRQ.getBookingCode(), flightData);
                flightAction.updateFlightPaymentSucceed(paymentSucceedRQ);
            } else {
                PaymentSucceedRQ paymentSucceedRQ = paymentPointByHotelSucceedRQ(paymentRQ.getBookingCode(), hotelData);
                hotelAction.updateHotelPaymentSucceed(paymentSucceedRQ);
            }

            return urlPaymentRS;
        }

        urlPaymentRS.setUrlPayment(appConfig.getPaymentPage() + "?bookingCode=" + paymentRQ.getBookingCode() +
            "&status=" + PaymentStatusConstant.PAYMENT_FAIL);

        return urlPaymentRS;
    }
}
