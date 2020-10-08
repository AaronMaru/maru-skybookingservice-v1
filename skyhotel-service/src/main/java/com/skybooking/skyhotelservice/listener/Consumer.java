package com.skybooking.skyhotelservice.listener;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.service.booking.BookingSV;
import com.skybooking.skyhotelservice.v1_0_0.service.payment.PaymentSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.skybooking.skyhotelservice.config.ActiveMQConfig.SKY_HOTEL_PAYMENT;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final PaymentSV paymentSV;
    private final BookingSV bookingSV;

    @JmsListener(destination = SKY_HOTEL_PAYMENT)
    public void payment(PaymentTransactionRQ paymentSucceedRQ) {

        // Store payment transactions
        HotelBookingEntity bookingEntity = paymentSV.saveBookingPayment(paymentSucceedRQ);

        PaymentMailRQ paymentMailRQ = new PaymentMailRQ(bookingEntity.getContactEmail(), bookingEntity.getCode(), paymentSucceedRQ.getLang());
        try {
            paymentSV.sendMailPaymentSuccess(paymentMailRQ);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Booking Proceed
//        BookingConfirmationHBRS bookingConfirmationHBRS = bookingSV.confirmBooking(bookingEntity, paymentSucceedRQ);

        bookingSV.confirmBooking(bookingEntity, paymentSucceedRQ);

        // Store booking
        try {
            paymentSV.sendPaymentInfoBookingSuccess(paymentMailRQ, paymentSucceedRQ.getBookingDetail());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
