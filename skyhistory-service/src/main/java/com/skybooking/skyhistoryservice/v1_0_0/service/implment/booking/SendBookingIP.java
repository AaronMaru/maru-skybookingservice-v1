package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking.BookingEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.SendBookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingEmailDetailRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.email.EmailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SendBookingIP implements SendBookingSV {

    @Autowired
    private BookingSV bookingSV;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private CompanyRP companyRP;

    @Autowired
    private Environment environment;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail receipt
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendBookingInfo(SendBookingPDFRQ sendBookingPDFRQ, String pdfTemplate, String Label) {

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingPDFRQ.getBookingCode());

        if (booking == null) {
            throw new BadRequestException("No booking data found", "");
        }

        BookingEmailDetailRS bookingEmailDetailRS = bookingSV.getBookingDetailEmail(booking.getId());

        Map<String, Object> mailData = emailBean.mailData(sendBookingPDFRQ.getEmail(), "Customer", 0,
                "your_flight_ticket", "booking-info", "api_receipt");
        mailData.put("data", bookingEmailDetailRS);

        Map<String, Object> pdfData = emailBean.dataPdfTemplate(pdfTemplate, Label);
        pdfData.put("data", bookingEmailDetailRS);

        // apply logo
        pdfData.put("logoPdf", this.embedLogo());

        List<String> sendData = Arrays.asList("receipt", "itinerary");
        pdfData.put("sendData", sendData);

        emailBean.sendEmailSMS("send-booking", mailData, pdfData);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail receipt and itinerary
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendBookingInfo(SendBookingNoAuthRQ sendBookingNoAuthRQ) {

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingNoAuthRQ.getBookingCode());

        if (booking == null) {
            throw new BadRequestException("No booking data found", "");
        }

        BookingEmailDetailRS bookingEmailDetailRS = bookingSV.getBookingDetailEmailWithoutAuth(booking.getId(),
                sendBookingNoAuthRQ);

        Map<String, Object> mailData = emailBean.mailData(sendBookingNoAuthRQ.getEmail(), "Customer", 0,
                "your_flight_ticket", "booking-info", "api_receipt");
        mailData.put("data", bookingEmailDetailRS);

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("label_receipt", emailBean.dataPdfTemplate("receipt", "api_receipt_pdf"));
        pdfData.put("data_receipt", bookingEmailDetailRS);

        pdfData.put("label_itinerary", emailBean.dataPdfTemplate("itinerary", "api_itinerary_pdf"));
        pdfData.put("data_itinerary", bookingEmailDetailRS);

        // apply logo
        pdfData.put("logoPdf", this.embedLogoNoAuth(sendBookingNoAuthRQ.getCompanyId()));

        List<String> sendData = Arrays.asList("receipt", "itinerary");
        pdfData.put("sendData", sendData);

        emailBean.sendReceiptAndItinerary("send-booking", mailData, pdfData);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendPayment(SendBookingPDFRQ sendBookingPDFRQ) {

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingPDFRQ.getBookingCode());
        System.out.println(booking);
        if (booking == null) {
            throw new BadRequestException("No booking data found", "");
        }

        BookingEmailDetailRS bookingEmailDetailRS = bookingSV.getBookingDetailEmail(booking.getId());

        Map<String, Object> mailData = emailBean.mailData(sendBookingPDFRQ.getEmail(), "Customer", 0,
                "flight_booking_successful_payment", "payment-success", "api_payment_succ");

        mailData.put("data", bookingEmailDetailRS);

        emailBean.sendEmailSMS("send-payment", mailData, null);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendPaymentWithoutAuth(SendBookingNoAuthRQ sendBookingNoAuthRQ) {

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingNoAuthRQ.getBookingCode());

        if (booking == null) {
            throw new BadRequestException("No booking data found", "");
        }

        BookingEmailDetailRS bookingEmailDetailRS = bookingSV.getBookingDetailEmailWithoutAuth(booking.getId(),
                sendBookingNoAuthRQ);

        Map<String, Object> mailData = emailBean.mailData(sendBookingNoAuthRQ.getEmail(), "Customer", 0,
                "flight_booking_successful_payment", "payment-success", "api_payment_succ");

        mailData.put("data", bookingEmailDetailRS);

        emailBean.sendEmailSMS("send-payment", mailData, null);

    }

    private String embedLogo() {
        String userType = jwtUtils.getClaim("userType", String.class);
        String companyLogo = "https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png";
        String profileOwner = jwtUtils.getClaim("profile", String.class);

        return userType.equals("skyowner") ? profileOwner : companyLogo;
    }

    private String embedLogoNoAuth(Integer companyId) {

        if (companyId == 0) {
            return "https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png";
        }

        StakeholderCompanyEntity stakeholderCompanyEntity = companyRP.findById(companyId.longValue()).orElse(null);
        return stakeholderCompanyEntity.getProfileImg() == null
                ? environment.getProperty("spring.awsImageUrl.companyProfile") + "/origin/default.png"
                : stakeholderCompanyEntity.getProfileImg();
    }
}
