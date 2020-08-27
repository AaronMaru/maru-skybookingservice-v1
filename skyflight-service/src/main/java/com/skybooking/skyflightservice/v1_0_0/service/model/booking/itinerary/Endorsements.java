package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Endorsements implements Serializable {
    @JsonProperty("Endorsement")
    private List<Endorsement> endorsement;
}
