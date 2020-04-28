package com.skybooking.skyhistoryservice.v1_0_0.transformer.mail;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingDetailTF {

    BookingInfoTF bookingInfo;
    ContactInfoRS contactInfo;
    PaymentInfoRS paymentInfo;

    List<BaggageInfoRS> baggageInfo = new ArrayList<>();
    List<ItineraryODInfoTF> itineraryInfo = new ArrayList<>();
    List<TicketInfoRS> ticketInfo   = new ArrayList<>();

    PriceInfoRS priceInfo;

}
