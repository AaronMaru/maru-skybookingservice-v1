package com.skybooking.eventservice.v1_0_0.ui.model.request.sms;

import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SkyPointRedeemSmsRQ {
    @NotNull(message = "Phone is missing.")
    private String phone;

    @NotNull(message = "TransactionFor is missing.")
    @IsIn(contains = {"FLIGHT", "HOTEL"}, message = "TransactionFor is invalid.")
    private String transactionFor;

    @NotNull(message = "BookingId is missing.")
    private String bookingId;
}
