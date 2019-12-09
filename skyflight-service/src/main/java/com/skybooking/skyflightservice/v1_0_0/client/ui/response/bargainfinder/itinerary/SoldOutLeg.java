package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SoldOutLeg {

    @JsonProperty("ref")
    private Integer id;

    @JsonProperty("brandCode")
    private String brandCode;

    @JsonProperty("brandDescription")
    private String brandDescription;

    @JsonProperty("programId")
    private String programId;

    @JsonProperty("programCode")
    private String programCode;

    @JsonProperty("programName")
    private String programName;

    @JsonProperty("programSystemCode")
    private String programSystemCode;

    @JsonProperty("soldOutSchedules")
    private List<SoldOutSchedule> soldOutSchedules = new ArrayList<>();

    @JsonProperty("status")
    private String status;
}
