package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BaggageDetail implements Serializable {

    private String id;
    private List<Baggage> details = new ArrayList<>();

}
