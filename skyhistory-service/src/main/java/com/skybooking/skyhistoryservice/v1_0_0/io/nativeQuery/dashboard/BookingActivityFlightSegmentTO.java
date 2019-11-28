package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

@Data
public class BookingActivityFlightSegmentTO {

    private String depLocode;
    private String depAirName;
    private String depCityName;
    private String arrLocode;
    private String arrAirName;
    private String arrCityName;

}
