package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.ticketing;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AccountTicketingTicketingTA implements Serializable {
    private Integer conjunctedCount;
}
