package com.skybooking.eventservice.v1_0_0.service.email;

import com.skybooking.eventservice.v1_0_0.ui.model.request.hotel.PaymentInfoRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.hotel.PaymentSuccessRQ;

public interface HotelEmailSV {
    void paymentSuccess(PaymentSuccessRQ paymentSucRQ);
    void paymentInfo(PaymentInfoRQ paymentInfoRQ);
}
