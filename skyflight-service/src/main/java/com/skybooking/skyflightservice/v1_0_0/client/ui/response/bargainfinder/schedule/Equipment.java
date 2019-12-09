package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Equipment {

    @JsonProperty("change")
    private Boolean change;

    @JsonProperty("code")
    private String code;

    @JsonProperty("typeForFirstLeg")
    private String typeForFirstLeg;

    @JsonProperty("typeForLastLeg")
    private String typeForLastLeg;

}
