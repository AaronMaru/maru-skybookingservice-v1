package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare.segment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FareSegment implements Serializable {

    @JsonProperty("extraMileageAllowance")
    private Boolean extraMileageAllowance;

    @JsonProperty("sideTrip")
    private SideTrip sideTrip;

    @JsonProperty("stopover")
    private Boolean stopover;

    @JsonProperty("stopoverCharge")
    private BigDecimal stopoverCharge;

    @JsonProperty("stopoverChargeCurrency")
    private String stopoverChargeCurrency;

    @JsonProperty("surcharges")
    private List<Surcharge> surcharges = new ArrayList<>();

    @JsonProperty("transferCharge")
    private BigDecimal transferCharge;

}
