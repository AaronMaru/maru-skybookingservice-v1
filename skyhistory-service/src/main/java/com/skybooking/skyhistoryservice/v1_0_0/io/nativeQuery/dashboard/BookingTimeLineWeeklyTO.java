package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigInteger;
import java.sql.Date;

@Data
public class BookingTimeLineWeeklyTO {
    private BigInteger total;
    private Date date;
}
