package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.validating;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidatingCarrier implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("ietCountryWobsp")
    private IETCountryWOBSP ietCountryWobsp;
}
