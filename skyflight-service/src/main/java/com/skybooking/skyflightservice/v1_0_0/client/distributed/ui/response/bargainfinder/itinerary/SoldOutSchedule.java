package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoldOutSchedule implements Serializable {

    @JsonProperty("ref")
    private Integer id;

    @JsonProperty("brandName")
    private String brandName;

    @JsonProperty("code")
    private String code;

    @JsonProperty("programId")
    private String programId;

    @JsonProperty("programCode")
    private String programCode;

    @JsonProperty("programDescription")
    private String programDescription;

    @JsonProperty("programSystemCode")
    private String programSystemCode;

    @JsonProperty("status")
    private String status;
}
