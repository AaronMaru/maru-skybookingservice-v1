package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenReservationElements implements Serializable {
    @JsonProperty("OpenReservationElement")
    private List<OpenReservationElement> openReservationElement;
}
