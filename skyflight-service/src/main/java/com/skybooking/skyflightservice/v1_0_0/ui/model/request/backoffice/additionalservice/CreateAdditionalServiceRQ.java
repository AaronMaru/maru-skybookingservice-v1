package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAdditionalServiceRQ extends UpdateAdditionalServiceRQ{

    @NotNull(message = "Booking id is required")
    private Integer bookingId;
}

