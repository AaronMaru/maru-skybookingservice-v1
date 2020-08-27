package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Tax implements Serializable {

    @JsonProperty("TaxCode")
    private String taxCode;

    @JsonProperty("Amount")
    private BigDecimal amount;

}
