package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.baggage.charge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaggageCharge implements Serializable {

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
