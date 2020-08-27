package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaxBreakdownCode implements Serializable {
    @JsonProperty("TaxPaid")
    private boolean taxPaid;
    private String content;
}
