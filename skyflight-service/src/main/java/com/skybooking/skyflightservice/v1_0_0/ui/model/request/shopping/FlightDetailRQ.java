package com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class FlightDetailRQ {

    @NotBlank(message = "The request ID is required.")
    private String requestId;

    @NotEmpty(message = "The legs ID is required.")
    @Size(min = 1, message = "The legs ID is required.")
    private String[] legIds;

}
