package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.baggage.allowance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaggageAllowance implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("pieceCount")
    private Integer pieceCount;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("weight")
    private Integer weight;

    @JsonProperty("description1")
    private String description1;

    @JsonProperty("description2")
    private String description2;

}
