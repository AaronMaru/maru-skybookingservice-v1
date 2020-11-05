package com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight;


import com.skybooking.backofficeservice.v1_0_0.ui.model.response.common.CompanyInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.common.ContactInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.common.PaymentInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.common.StakeholderUserInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.flightDetail.BookingInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.flightDetail.ItineraryInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.flightDetail.PriceInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.flightDetail.TicketInfo;
import lombok.Data;

import java.util.List;

@Data
public class FlightDetailRS{
    private BookingInfo bookingInfo;
    private StakeholderUserInfo stakeholderUserInfo;
    private CompanyInfo companyInfo;
    private ContactInfo contactInfo;
    private List<ItineraryInfo> itineraryInfo;
    private List<TicketInfo> ticketInfo;
    private PaymentInfo paymentInfo;
    private PriceInfo priceInfo;
}
