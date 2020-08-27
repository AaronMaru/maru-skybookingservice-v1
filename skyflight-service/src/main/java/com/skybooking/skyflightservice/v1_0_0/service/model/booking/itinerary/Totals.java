package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Totals implements Serializable {
    @JsonProperty("BaseFare")
    private BaseFare baseFare;
    @JsonProperty("TotalFare")
    private TotalFare totalFare;
    @JsonProperty("Taxes")
    private Taxes taxes;
}
