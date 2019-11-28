package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
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
    private List<BookingActivityFlightSegmentRS> segments = new ArrayList();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp createdAt;
}
