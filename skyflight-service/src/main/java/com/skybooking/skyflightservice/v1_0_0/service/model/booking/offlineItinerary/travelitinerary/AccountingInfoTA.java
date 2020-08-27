package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AccountingInfoTA implements Serializable {
    private Integer id;
    private String fareApplication;
    private AccountBaseFareTA baseFare;
    private AccountPersonNameTA personName;
    private AccountAirlineTA airline;
    private AccountTicketingInfoTA ticketingInfo;
    private AccountPaymentInfoTA paymentInfo;
    private AccountDocumentInfoTA documentInfo;
    private AccountTaxesTA taxes;
}
