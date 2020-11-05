package com.skybooking.paymentservice.v1_0_0.ui.controller;

import com.skybooking.paymentservice.constant.BookingStatusConstant;
import com.skybooking.paymentservice.v1_0_0.client.flight.action.FlightAction;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.hotel.action.HotelAction;
import com.skybooking.paymentservice.v1_0_0.client.point.action.PointAction;
import com.skybooking.paymentservice.v1_0_0.client.point.ui.PointRQ;
import com.skybooking.paymentservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.paymentservice.v1_0_0.service.implement.paymentForm.PaymentFormIP;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.PipaySV;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.paymentservice.v1_0_0.util.encrypt.AESEncryptionDecryption;
import com.skybooking.paymentservice.v1_0_0.util.integration.Payments;
import com.skybooking.paymentservice.v1_0_1.ui.model.response.StructureRS;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PaymentFormController {

    @Autowired
    private Payments payments;

    @Autowired
    private FlightAction flightAction;

    @Autowired
    private HotelAction hotelAction;

    @Autowired
    private PointAction pointAction;

    @Autowired
    private ProviderSV providerSV;

    @Autowired
    private ActivityLoggingBean activityLog;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private PipaySV pipaySV;

    @Autowired
    private PaymentFormIP paymentFormIP;

    @Autowired
    private Environment environment;
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request Payment Form PIPAY
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/pipay/form")
    public String pipayForm(@RequestParam Map<String, String> request, Model model) {

        var dataToken = payments.upadatePayment(request);

        if (dataToken.getBookingCode().contains("SBFT")) {
            var booking = bookingRP.getBooking(dataToken.getBookingCode());
            booking.setStatus(BookingStatusConstant.PAYMENT_CREATED);
            bookingRP.save(booking);

            activityLog.activities(ActivityLoggingBean.Action.INDEX_CREATE_PAYMENT, activityLog.getUser(booking.getStakeholderUserId()), booking);
        }

        if (dataToken.getRender() == 1) {
            return "error";
        }

        payments.updateUrlToken(dataToken.getId());

        if (dataToken.getBookingCode().contains("SBHT")) {
            payments.pipayPayload(payments.getPaymentInfoHotel(dataToken, hotelAction.getMandatoryData(dataToken.getBookingCode())), model);
        }
        if (dataToken.getBookingCode().contains("SBFT")) {
            payments.pipayPayload(payments.getPaymentInfo(dataToken, flightAction.getMandatoryData(dataToken.getBookingCode())), model);
        }
        if (dataToken.getBookingCode().contains("PTS")) {
            PointRQ pointRQ = new PointRQ();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", dataToken.getBookingCode());

            String key = environment.getProperty("encryption.key");
            String initVector = environment.getProperty("encryption.iv");

            try {
                pointRQ.setData(AESEncryptionDecryption.encrypt(jsonObject.toString(), key, initVector));
            } catch (Exception e) {
                e.printStackTrace();
            }

            StructureRS structureRS = pointAction.getMandatoryData(pointRQ);

            Map<String, Object> transactionData = structureRS.getData();
            FlightMandatoryDataRS flightMandatoryDataRS = new FlightMandatoryDataRS();
            Double amount = (Double) transactionData.get("amount");
            flightMandatoryDataRS.setDescription((String) transactionData.get("description"));
            flightMandatoryDataRS.setAmount(BigDecimal.valueOf(amount));
            flightMandatoryDataRS.setSkyuserId((Integer) transactionData.get("stakeholderUserId"));
            flightMandatoryDataRS.setBookingCode((String) transactionData.get("code"));
            flightMandatoryDataRS.setCompanyId((Integer) transactionData.get("stakeholderCompanyId"));
            flightMandatoryDataRS.setName((String) transactionData.get("name"));
            flightMandatoryDataRS.setEmail((String) transactionData.get("email"));
            flightMandatoryDataRS.setPhoneNumber((String) transactionData.get("phoneNumber"));

            PaymentRS paymentRS = payments.getPaymentInfo(dataToken, flightMandatoryDataRS);
            payments.pipayPayload(paymentRS, model);
        }
        return "pipay/form";

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request Payment Form For IPAY88
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/ipay88/form")
    public String ipay88Form(@RequestParam Map<String, String> request, Model model) {

        var dataToken = payments.upadatePayment(request);
        String bookingCode = dataToken.getBookingCode();

        //Hotel
        if (bookingCode.contains("SBHT")) {
            return paymentFormIP.ipay88Form(request, model);
        }

        if (bookingCode.contains("SBFT")) {
            var booking = bookingRP.getBooking(bookingCode);
            booking.setStatus(BookingStatusConstant.PAYMENT_CREATED);

            bookingRP.save(booking);
            activityLog.activities(ActivityLoggingBean.Action.INDEX_CREATE_PAYMENT, activityLog.getUser(booking.getStakeholderUserId()), booking);
        }

        if (dataToken.getRender() == 1) {
            return "error";
        }

        payments.updateUrlToken(payments.upadatePayment(request).getId());
        if (bookingCode.contains("SBFT")) {
            payments.ipay88Payload(payments.getPaymentInfo(dataToken, flightAction.getMandatoryData(dataToken.getBookingCode())), model);
        } else {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", bookingCode);

            String initVector = environment.getProperty("encryption.iv");
            String key = environment.getProperty("encryption.key");

            PointRQ pointRQ = new PointRQ();
            try {
                pointRQ.setData(AESEncryptionDecryption.encrypt(jsonObject.toString(), key, initVector));
            } catch (Exception e) {
                e.printStackTrace();
            }

            StructureRS structureRS = pointAction.getMandatoryData(pointRQ);

            Map<String, Object> transactionData = structureRS.getData();
            Double amount = (Double) transactionData.get("amount");
            FlightMandatoryDataRS flightMandatoryDataRS = new FlightMandatoryDataRS();
            flightMandatoryDataRS.setAmount(BigDecimal.valueOf(amount));
            flightMandatoryDataRS.setDescription((String) transactionData.get("description"));
            flightMandatoryDataRS.setSkyuserId((Integer) transactionData.get("stakeholderUserId"));
            flightMandatoryDataRS.setCompanyId((Integer) transactionData.get("stakeholderCompanyId"));
            flightMandatoryDataRS.setBookingCode((String) transactionData.get("code"));
            flightMandatoryDataRS.setName((String) transactionData.get("name"));
            flightMandatoryDataRS.setEmail((String) transactionData.get("email"));
            flightMandatoryDataRS.setPhoneNumber((String) transactionData.get("phoneNumber"));

            PaymentRS paymentRS = payments.getPaymentInfo(dataToken, flightMandatoryDataRS);
            payments.ipay88Payload(paymentRS, model);

        }

        return "ipay88/form";

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get response from IPay88 and check it's succeed or fail. If it's succeed, it is going to issue air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/ipay88/response", consumes = {MediaType.ALL_VALUE})
    public RedirectView ipay88Response(@RequestParam Map<String, Object> request) {

        var params = request.entrySet().stream().map(item -> item.getKey().concat("=").concat(item.getValue().toString())).collect(Collectors.joining("&"));
        String bookingCode = (String) request.get("RefNo");

        if (bookingCode.contains("SBFT")) {
            var booking = bookingRP.getBooking(bookingCode);
            booking.setLogPaymentRes(params);
            booking.setStatus(BookingStatusConstant.PAYMENT_PROCESSING);

            bookingRP.save(booking);

            var action = request.get("Status").equals("1") ? ActivityLoggingBean.Action.INDEX_TICKETING_PROCESSING_PAYMENT : ActivityLoggingBean.Action.FAIL_IPAY88;

            activityLog.activities(action, activityLog.getUser(booking.getStakeholderUserId()), booking);
        }

        var redirectURI = providerSV.getIpay88Response(request);

        System.out.println(String.format("Redirecting to: %s", redirectURI));

        return new RedirectView(redirectURI);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get pipay response success and continue issue air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @GetMapping("/pipay/success")
    public ResponseEntity<Void> pipaySucceed(@RequestParam Map<String, Object> request) {

        String bookingCode = (String) request.get("orderID");
        var params = request.entrySet().stream().map(item -> item.getKey().concat("=").concat(item.getValue().toString())).collect(Collectors.joining("&"));

        if (bookingCode.contains("SBFT")) {
            var booking = bookingRP.getBooking(bookingCode);
            booking.setLogPaymentRes(params);
            booking.setStatus(BookingStatusConstant.PAYMENT_PROCESSING);
            bookingRP.save(booking);
            activityLog.activities(ActivityLoggingBean.Action.INDEX_TICKETING_PROCESSING_PAYMENT, activityLog.getUser(booking.getStakeholderUserId()), booking);
        }
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(providerSV.getPipaySucceedResponse(request)))
                .build();

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get pipay response fail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @GetMapping("/pipay/fail")
    public ResponseEntity<Void> pipayFail(@RequestParam Map<String, Object> request) {

        var params = request.entrySet().stream().map(item -> item.getKey().concat("=").concat(item.getValue().toString())).collect(Collectors.joining("&"));

        String bookingCode = (String) request.get("orderID");

        var booking = bookingRP.getBooking(bookingCode);
        var user = activityLog.getUser(booking.getStakeholderUserId());
        booking.setStatus(BookingStatusConstant.PAYMENT_PROCESSING);
        booking.setLogPaymentRes(params);

        bookingRP.save(booking);

        var action = request.get("status").equals("0200") ? ActivityLoggingBean.Action.CANCELLATION_PIPAY : ActivityLoggingBean.Action.FAIL_PIPAY;

        activityLog.activities(action, user, booking);

        pipaySV.paymentFail(request);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(providerSV.getPipayFailResponse(request)))
                .build();
    }


}
