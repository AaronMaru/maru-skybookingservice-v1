package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDescription implements Serializable {

    @JsonProperty("itineraryType")
    private String itineraryType;

    @JsonProperty("legDescriptions")
    private List<LegDescription> legDescriptions = new ArrayList<>();
}
