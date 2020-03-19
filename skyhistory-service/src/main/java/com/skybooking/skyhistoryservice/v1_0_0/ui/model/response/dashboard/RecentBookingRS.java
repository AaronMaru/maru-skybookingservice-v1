package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class RecentBookingRS {

    private Integer bookingId;
    private String bookingCode;
    private String TripType;
    private Integer skyuserId;
    private String skyuserName;
    private String skyuserPhoto;
    private BigDecimal totalAmount;
    private String currencyCode;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
//    private Date createdAt;
    private Object createdAt;
    private List<RecentBookingSegmentRS> segments = new ArrayList<>();

}
