package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAdditionalServiceRQ extends UpdateAdditionalServiceRQ{

    @NotNull(message = "REQUIRE_BOOKING_ID")
    private Integer bookingId;
}

