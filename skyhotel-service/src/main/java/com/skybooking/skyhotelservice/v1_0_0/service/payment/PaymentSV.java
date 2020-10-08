package com.skybooking.skyhotelservice.v1_0_0.service.payment;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.BookingDetailRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentMandatoryRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;

public interface PaymentSV {
    PaymentMandatoryRS payment(PaymentMandatoryRQ paymentRQ);
    PaymentMandatoryRS getMandatoryData(String bookingCode);
    void updatePaymentSucceed(PaymentTransactionRQ paymentSucceedRQ);
    HotelBookingEntity saveBookingPayment(PaymentTransactionRQ paymentSucceedRQ);
    void sendMailPaymentSuccess(PaymentMailRQ paymentMailRQ);
    void sendPaymentInfoBookingSuccess(PaymentMailRQ paymentMailRQ, BookingDetailRS bookingDetail);
    void sendMailPaymentInfo(PaymentMailRQ paymentMailRQ);
}
