package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.destination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDestinationRS {
    private String groupBy;
    private String groupName;
    private List<DestinationRS> destinations;
}
