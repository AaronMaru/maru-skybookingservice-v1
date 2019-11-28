package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecentBookingRS {

    private Integer bookingId;
    private String bookingCode;
    private String TripType;
    private String contName;
    private String contPhoto;
    private BigDecimal totalAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp createdAt;
    private List<RecentBookingSegmentRS> segments = new ArrayList<>();

}
