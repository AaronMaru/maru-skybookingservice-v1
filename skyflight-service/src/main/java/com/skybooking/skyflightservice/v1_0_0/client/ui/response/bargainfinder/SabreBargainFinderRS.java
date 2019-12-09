package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SabreBargainFinderRS {
    @JsonProperty("groupedItineraryResponse")
    private ItineraryResponse itineraryResponse;
}
