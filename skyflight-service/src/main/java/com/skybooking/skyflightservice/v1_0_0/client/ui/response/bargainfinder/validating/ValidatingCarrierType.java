package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.validating;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidatingCarrierType {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("country")
    private String country;

    @JsonProperty("default")
    private ValidatingCarrier defaultValidate;

    @JsonProperty("newVcxProcess")
    private Boolean newVcxProcess;

    @JsonProperty("settlementMethod")
    private String settlementMethod;

    @JsonProperty("otherTicketings")
    private List<ValidatingCarrier> otherTicketings = new ArrayList<>();

    @JsonProperty("alternates")
    private List<ValidatingCarrier> alternates = new ArrayList<>();

}
