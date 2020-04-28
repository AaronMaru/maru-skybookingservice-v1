package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class BookingTimelineRS {
    private BigInteger total = BigInteger.ZERO;
    private String date;
    private String duringKey;
}
