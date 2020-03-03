package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;

public interface SendBookingSV {

    void sendBookingInfo(SendBookingPDFRQ sendBookingPDFRQ, String pdfTemplate, String Label);

    void sendBookingInfo(SendBookingNoAuthRQ sendBookingNoAuthRQ);

    void sendPayment(SendBookingPDFRQ sendBookingPDFRQ);

    void sendPaymentWithoutAuth(SendBookingNoAuthRQ sendBookingNoAuthRQ);
}
