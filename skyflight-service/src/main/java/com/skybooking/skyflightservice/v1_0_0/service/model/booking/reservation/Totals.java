package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Totals implements Serializable {
    @JsonProperty("Total")
    private Total total;
    private String code;
    @JsonProperty("TotalTax")
    private TotalTax totalTax;
    @JsonProperty("Base")
    private Base base;
}
