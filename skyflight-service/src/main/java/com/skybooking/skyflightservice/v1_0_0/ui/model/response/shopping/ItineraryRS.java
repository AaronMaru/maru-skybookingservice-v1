package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryRS implements Serializable {

    private String airline = "";
    private List<LegGroupRS> legGroups = new ArrayList<>();
    private List<String> lowest = new ArrayList<>();

}
