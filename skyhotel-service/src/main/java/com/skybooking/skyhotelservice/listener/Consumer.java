package com.skybooking.skyhotelservice.listener;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.service.payment.PaymentIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.skybooking.skyhotelservice.config.ActiveMQConfig.SKY_HOTEL_PAYMENT;

@Component
public class Consumer {

    @Autowired
    private PaymentIP paymentIP;

    @JmsListener(destination = SKY_HOTEL_PAYMENT)
    public void payment(PaymentTransactionRQ paymentSucceedRQ) {

        HotelBookingEntity bookingEntity = paymentIP.saveBookingPayment(paymentSucceedRQ);
    }

}
