package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking.BookingEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.SendBookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingEmailDetailRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.general.ApiBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.general.DuplicateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SendBookingIP implements SendBookingSV {


    @Autowired
    private ApiBean apiBean;

    @Autowired
    private BookingSV bookingSV;

    @Autowired
    private DuplicateBean duplicate;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private JwtUtils jwtUtils;

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

        Map<String, Object> mailData = duplicate.mailData(sendBookingPDFRQ.getEmail(),"Customer", 0, "your_flight_ticket",
                "booking-info", "api_receipt");
        mailData.put("data", bookingEmailDetailRS);

        Map<String, Object> pdfData = duplicate.dataPdfTemplate(pdfTemplate, Label);
        pdfData.put("data", bookingEmailDetailRS);

        String userType = jwtUtils.getClaim("userType", String.class);
        String companyLogo = "https://s3.amazonaws.com/skybooking/uploads/mail/images/logo.png";
        String profileOwner = jwtUtils.getClaim("profile", String.class);

        pdfData.put("logoPdf", userType.equals("skyowner") ? profileOwner : companyLogo) ;

        apiBean.sendEmailSMS( "send-booking", mailData,
                pdfData);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     */
    public void sendPayment(SendBookingPDFRQ sendBookingPDFRQ) {

        BookingEntity booking = bookingRP.findByBookingCode(sendBookingPDFRQ.getBookingCode());

        if (booking == null) {
            throw new BadRequestException("No booking data found", "");
        }

        BookingEmailDetailRS bookingEmailDetailRS= bookingSV.getBookingDetailEmail(booking.getId());

        Map<String, Object> mailData = duplicate.mailData(sendBookingPDFRQ.getEmail(),"Customer", 0,
                    "flight_booking_successful_payment", "payment-success", "api_payment_succ");

        mailData.put("data", bookingEmailDetailRS);

        apiBean.sendEmailSMS( "send-payment", mailData, null);

    }



}
