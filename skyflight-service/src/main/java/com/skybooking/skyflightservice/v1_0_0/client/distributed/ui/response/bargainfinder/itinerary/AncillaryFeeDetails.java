package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AncillaryFeeDetails implements Serializable {

    @JsonProperty("ancillaryTypeCode")
    private String ancillaryTypeCode;

    @JsonProperty("baggageId")
    private Integer baggageId;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("code")
    private String code;

    @JsonProperty("subcode")
    private String subcode;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("startSegment")
    private Integer startSegment;

    @JsonProperty("endSegment")
    private Integer endSegment;

    @JsonProperty("departureDate")
    private String departureDate;

    @JsonProperty("description")
    private String description;

}
