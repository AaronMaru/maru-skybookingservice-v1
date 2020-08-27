package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FormOfPayment implements Serializable {
    @JsonProperty("Check")
    private Check check;
    private boolean migrated;
}
