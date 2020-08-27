package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.ticketing.AccountTicketingETicketTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.ticketing.AccountTicketingExchangeTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.ticketing.AccountTicketingTicketingTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AccountTicketingInfoTA implements Serializable {
    private String tariffBasis;
    private AccountTicketingExchangeTA exchange;
    private AccountTicketingETicketTA eTicket;
    private AccountTicketingTicketingTA ticketing;
}
