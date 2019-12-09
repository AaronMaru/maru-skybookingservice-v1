package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.fare.segment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Surface {

    @JsonProperty("extraMileageAllowance")
    private Boolean extraMileageAllowance;

    @JsonProperty("stopover")
    private Boolean stopover;

    @JsonProperty("stopoverCharge")
    private BigDecimal stopoverCharge;

    @JsonProperty("stopoverChargeCurrency")
    private String stopoverChargeCurrency;

    @JsonProperty("unchargeable")
    private Boolean unchargeable;

}
