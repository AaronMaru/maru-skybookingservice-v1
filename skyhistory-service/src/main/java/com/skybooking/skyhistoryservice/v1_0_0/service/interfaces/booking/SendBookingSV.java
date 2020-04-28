package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.PrintRS;

public interface SendBookingSV {

    void sendBookingInfo(SendBookingPDFRQ sendBookingPDFRQ, String pdfTemplate, String Label);

    void sendBookingInfo(SendBookingNoAuthRQ sendBookingNoAuthRQ);

    void sendPayment(SendBookingPDFRQ sendBookingPDFRQ);

    void sendPaymentWithoutAuth(SendBookingNoAuthRQ sendBookingNoAuthRQ);

    PrintRS printBooking(String bookingCode);

}
