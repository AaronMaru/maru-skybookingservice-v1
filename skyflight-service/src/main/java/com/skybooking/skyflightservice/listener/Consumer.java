package com.skybooking.skyflightservice.listener;

import com.skybooking.skyflightservice.v1_0_0.client.skyhistory.action.SkyhistoryAction;
import com.skybooking.skyflightservice.v1_0_0.client.skyhistory.request.SendBookingPDFRQ;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.service.implement.payment.PaymentIP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.TicketSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentTransactionRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.ticketing.TicketRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.skybooking.skyflightservice.config.ActiveMQConfig.SKY_FLIGHT_PAYMENT;

/**
 * Created by : maru
 * Date  : 1/22/2020
 * Time  : 4:47 PM
 */

@Component
public class Consumer {

    @Autowired
    private SkyhistoryAction skyhistoryAction;

    @Autowired
    private TicketSV ticketSV;

    @Autowired
    private PaymentIP paymentIP;

    @JmsListener(destination = SKY_FLIGHT_PAYMENT)
    public void payment(PaymentTransactionRQ paymentSucceedRQ) {

        BookingEntity bookingEntity = paymentIP.saveBookingPayment(paymentSucceedRQ);

        // hit payment skyHistory
         SendBookingPDFRQ sendBookingPDFRQ = new SendBookingPDFRQ(bookingEntity.getBookingCode(),
                paymentSucceedRQ.getEmail(), paymentSucceedRQ.getSkyuserId(), paymentSucceedRQ.getCompanyId());

        skyhistoryAction.sendPayment(sendBookingPDFRQ);

        TicketRS ticketRS = ticketSV.issued(sendBookingPDFRQ.getBookingCode());

        if (ticketRS.getStatus().equals("Complete")) {
            // hit receipt and itineray
            skyhistoryAction.sendReceiptWithItinerary(sendBookingPDFRQ);

        }
    }

}
