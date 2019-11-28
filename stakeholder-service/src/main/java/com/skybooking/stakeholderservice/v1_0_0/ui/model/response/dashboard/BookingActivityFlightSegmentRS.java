package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

@Data
public class BookingActivityFlightSegmentRS {

    private String depLocode;
    private String depAirName;
    private String depCityName;
    private String arrLocode;
    private String arrAirName;
    private String arrCityName;

}
