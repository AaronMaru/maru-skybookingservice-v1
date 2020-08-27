package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.payment.AccountPaymentCommissionTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.payment.AccountPaymentPaymentTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AccountPaymentInfoTA implements Serializable {
    private AccountPaymentPaymentTA payment;
    private AccountPaymentCommissionTA commission;
}
