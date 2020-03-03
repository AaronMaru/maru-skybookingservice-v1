package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Itinerary implements Serializable {

    private String id;
    private String airline;
    private List<LegGroup> legGroups = new ArrayList<>();
    private List<String> lowest = new ArrayList<>();
    private List<String> highest = new ArrayList<>();
    private boolean favorite;

}
