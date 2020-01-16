package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reissue implements Serializable {

    @JsonProperty("electronicTicketNotAllowed")
    private Boolean electronicTicketNotAllowed;

    @JsonProperty("electronicTicketRequired")
    private Boolean electronicTicketRequired;

    @JsonProperty("formOfRefund")
    private String formOfRefund;

    @JsonProperty("residual")
    private String residual;

    @JsonProperty("tag7Result")
    private Boolean tag7Result;

    @JsonProperty("changeFees")
    private List<ChangeFee> changeFees = new ArrayList<>();
}
