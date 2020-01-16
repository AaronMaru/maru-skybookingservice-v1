package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Carrier implements Serializable {

    @JsonProperty("alliances")
    private String alliances;

    @JsonProperty("basicBookingRequest")
    private Boolean basicBookingRequest;

    @JsonProperty("callDirect")
    private Boolean callDirect;

    @JsonProperty("codeShared")
    private String codeShared;

    @JsonProperty("disclosure")
    private String disclosure;

    @JsonProperty("equipment")
    private Equipment equipment;

    @JsonProperty("marketing")
    private String marketing;

    @JsonProperty("marketingFlightNumber")
    private Integer marketingFlightNumber;

    @JsonProperty("operating")
    private String operating;

    @JsonProperty("operatingFlightNumber")
    private Integer operatingFlightNumber;
}
