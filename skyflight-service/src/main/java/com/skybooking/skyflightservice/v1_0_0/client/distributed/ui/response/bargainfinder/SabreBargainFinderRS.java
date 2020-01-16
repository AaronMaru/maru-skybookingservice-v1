package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SabreBargainFinderRS implements Serializable {
    @JsonProperty("groupedItineraryResponse")
    private ItineraryResponse itineraryResponse;
}
