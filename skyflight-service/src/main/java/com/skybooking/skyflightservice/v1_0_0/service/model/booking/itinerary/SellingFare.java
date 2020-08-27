package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SellingFare implements Serializable {

    @JsonProperty("BaseFare")
    private String baseFare;
    @JsonProperty("TotalFare")
    private TotalFare totalFare;
    @JsonProperty("EquivFare")
    private String equivFare;
    @JsonProperty("Taxes")
    private String taxes;

}
