package com.skybooking.skyhistoryservice.v1_0_0.transformer.mail;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryODInfoTF {

    private Integer elapsedTime;
    private String elapsedHourMinute;
    private Boolean multiAirlineStatus;
    private String multiAirlineUrl;
    private Integer adjustmentDate;
    private Integer stop;

    List<ItineraryODSegmentTF> itinerarySegment = new ArrayList<>();

}
