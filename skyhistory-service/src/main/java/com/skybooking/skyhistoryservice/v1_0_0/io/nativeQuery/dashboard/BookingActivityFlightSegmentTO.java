package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.util.Date;

@Data
public class BookingActivityFlightSegmentTO {

    private String depLocode;
    private String depAirName;
    private String depCityName;
    private Date depDateTime;
    private String arrLocode;
    private String arrAirName;
    private String arrCityName;

}
