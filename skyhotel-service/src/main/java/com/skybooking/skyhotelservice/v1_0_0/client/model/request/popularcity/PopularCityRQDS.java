package com.skybooking.skyhotelservice.v1_0_0.client.model.request.popularcity;

import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.DestinationRQDS;
import lombok.Data;

import java.util.List;

@Data
public class PopularCityRQDS {

    private List<DestinationRQDS> destinations;

}
