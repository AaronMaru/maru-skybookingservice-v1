package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SellingFareDetails implements Serializable {
    @JsonProperty("FareCalc")
    private String fareCalc;
    @JsonProperty("TotalFare")
    private TotalFare totalFare;
    @JsonProperty("Taxes")
    private SellingFareDetailTaxes taxes;
    @JsonProperty("TotalTax")
    private TotalTax totalTax;
    @JsonProperty("BaseFare")
    private BaseFare baseFare;
}
