package com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice;

import com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.offlineitinerary.PassengerInfoRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.offlineitinerary.PaymentInfoRS;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CheckOfflineItineraryRS {
    private BigDecimal amount;
    private BigDecimal commission;
    private List<PassengerInfoRS> passengerInfo;
    private PaymentInfoRS paymentInfo;
}
