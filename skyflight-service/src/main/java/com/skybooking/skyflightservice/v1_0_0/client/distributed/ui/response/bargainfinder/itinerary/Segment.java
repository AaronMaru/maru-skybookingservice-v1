package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Segment implements Serializable {

    @JsonProperty("bookingCode")
    private String bookingCode;

    @JsonProperty("cabinCode")
    private String cabinCode;

    @JsonProperty("dualInventoryCode")
    private String dualInventoryCode;

    @JsonProperty("mealCode")
    private String mealCode;

    @JsonProperty("seatsAvailable")
    private Integer seatsAvailable;

    @JsonProperty("availabilityBreak")
    private Boolean availabilityBreak;

}
