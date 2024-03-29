package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BaggageDetailRS implements Serializable {

    private String id;
    private List<BaggageRS> details = new ArrayList<>();

}
