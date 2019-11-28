package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigInteger;
import java.sql.Date;

@Data
public class BookingTimelineRS {
    private BigInteger total;
    private Date date;
}
