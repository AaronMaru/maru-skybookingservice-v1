package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FlightLegRQ {

    @NotBlank(message = "The bargainfinder origin is required.")
    private String origin;

    @NotBlank(message = "The bargainfinder destination is required.")
    private String destination;

    @NotNull(message = "The departure date is required.")
    private String date;

}
