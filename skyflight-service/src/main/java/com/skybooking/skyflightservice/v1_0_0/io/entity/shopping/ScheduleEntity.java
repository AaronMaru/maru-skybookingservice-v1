package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScheduleEntity implements Serializable {

    private String id;
    private String externalId;
    private String departurePlaceId;
    private String arrivalPlaceId;
    private String departure;
    private String arrival;
    private int stopCount;

}
