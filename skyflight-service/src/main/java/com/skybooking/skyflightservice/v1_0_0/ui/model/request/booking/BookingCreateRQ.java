package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import com.skybooking.skyflightservice.exception.anotation.ShoppingCached;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@ShoppingCached
public class BookingCreateRQ {

    @NotBlank(message = "The request ID is required.")
    private String requestId;

    @NotEmpty(message = "The legs ID is required.")
    @Size(min = 1, message = "The legs ID is required.")
    private String[] legIds;

    @NotNull(message = "The flight's person contact information is required.")
    @Valid
    private BookingContactRQ contact;

    @Size(min = 1, message = "The flight's passenger information is required.")
    @Valid
    private List<BookingPassengerRQ> passengers = new ArrayList<>();
}
