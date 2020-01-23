package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class BCreateRQ {

    @NotBlank(message = "The request ID is required.")
    private String requestId;

    @NotEmpty(message = "The legs ID is required.")
    @Size(min = 1, message = "The legs ID is required.")
    private String[] legIds;

//    @NotNull(message = "The flight's booking company information is required.")
//    @Valid
//    private BCompanyRQ company;

    @NotNull(message = "The flight's person contact information is required.")
    @Valid
    private BContactRQ contact;

    @Size(min = 1, message = "The flight's passenger information is required.")
    @Valid
    private List<BPassengerRQ> passengers = new ArrayList<>();
}
