package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import com.skybooking.skyflightservice.constant.TripTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FlightShopping implements Serializable {

    private int adult;
    private int child;
    private int infant;
    private TripTypeEnum tripType;
    private String classType;
    private List<FlightLeg> legs = new ArrayList();

    public FlightShopping(int adult, int child, int infant, TripTypeEnum tripType, String classType, List<FlightLeg> legs) {
        this.adult = adult;
        this.child = child;
        this.infant = infant;
        this.tripType = tripType;
        this.classType = classType;
        this.legs = legs;
    }

}
