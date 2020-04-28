package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookingActivityRS {

    private Integer bookingId;
    private String bookingCode;
    private String userName;
    private String userProfile;
    private String bookingType;
    private String description;
    private String tripType;
    private String createdAt;
    private String statusKey;
    private List<BookingActivityFlightSegmentRS> legs = new ArrayList();

}
