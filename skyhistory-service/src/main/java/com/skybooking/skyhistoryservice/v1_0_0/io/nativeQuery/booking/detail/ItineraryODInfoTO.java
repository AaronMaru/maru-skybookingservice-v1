package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import lombok.Data;

@Data
public class ItineraryODInfoTO {

    private Integer id;
    private Integer elapsedTime;
    private Boolean multiAirlineStatus;
    private String multiAirlineUrl;
    private Integer adjustmentDate;
    private Integer stop;

}
