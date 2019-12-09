package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.validating;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Country {
    @JsonProperty("code")
    private String code;
}
