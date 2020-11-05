package com.skybooking.backofficeservice.v1_0_0.client.model.response.flight;

import com.skybooking.backofficeservice.v1_0_0.client.model.response.common.ContactInfo;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.common.PaymentInfo;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.detail.*;
import lombok.Data;

import java.util.List;

@Data
public class FlightDetailServiceRS {
    private BookingInfo bookingInfo;
    private ContactInfo contactInfo;
    private List<ItineraryInfo> itineraryInfo;
    private List<TicketInfo> ticketInfo;
    private PaymentInfo paymentInfo;
    private PriceInfo priceInfo;
}
