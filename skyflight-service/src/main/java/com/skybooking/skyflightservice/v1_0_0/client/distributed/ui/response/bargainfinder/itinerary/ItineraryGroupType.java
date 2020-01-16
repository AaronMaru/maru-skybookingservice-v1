package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItineraryGroupType implements Serializable {

    @JsonProperty("groupDescription")
    private GroupDescription groupDescription;

    @JsonProperty("itineraries")
    private List<Itinerary> itineraries = new ArrayList<>();

    @JsonProperty("processingMessages")
    private List<ProcessingMessage> processingMessages = new ArrayList<>();
}
