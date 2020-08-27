package com.skybooking.skyhotelservice.v1_0_0.client.model.request.content;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OccupancyRQDS {

    private int rooms;
    private int adults;
    private int children = 0;
    private List<PaxRQDS> paxes;

}
