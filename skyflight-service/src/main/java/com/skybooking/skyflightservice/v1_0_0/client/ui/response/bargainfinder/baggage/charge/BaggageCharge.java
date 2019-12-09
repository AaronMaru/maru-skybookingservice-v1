package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.baggage.charge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaggageCharge {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("firstPiece")
    private Integer firstPiece;

    @JsonProperty("lastPiece")
    private Integer lastPiece;

    @JsonProperty("equivalentAmount")
    private BigDecimal equivalentAmount;

    @JsonProperty("equivalentCurrency")
    private String equivalentCurrency;

    @JsonProperty("noChargeNotAvailable")
    private String noChargeNotAvailable;

    @JsonProperty("description1")
    private String description1;

    @JsonProperty("description2")
    private String description2;
}
