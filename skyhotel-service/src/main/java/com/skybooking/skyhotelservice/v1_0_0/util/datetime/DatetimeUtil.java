package com.skybooking.skyhotelservice.v1_0_0.util.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DatetimeUtil {

    public static long night(String checkIn, String checkOut) {

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date startDate = formatDate.parse(checkIn);
            Date endDate = formatDate.parse(checkOut);
            long diff = (endDate.getTime() - startDate.getTime());

            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (ParseException e) {

            e.printStackTrace();
            return 0;

        }
    }

    public static Date toDate(String s) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            return formatDate.parse(s);
        } catch (Exception exception) {
            return null;
        }
    }

}
