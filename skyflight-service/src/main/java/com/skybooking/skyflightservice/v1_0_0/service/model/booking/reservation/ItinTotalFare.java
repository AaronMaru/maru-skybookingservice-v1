package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItinTotalFare implements Serializable {

    @JsonProperty("Total")
    private Total total;
    private String code;
    @JsonProperty("Taxes")
    private Taxes taxes;
    @JsonProperty("Base")
    private Base base;
    @JsonProperty("Totals")
    private Totals totals;

}
