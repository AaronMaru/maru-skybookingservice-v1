package com.skybooking.skyhistoryservice.v1_0_0.util.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeBean {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Date to iso date
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param date
     */
    public String convertDateTime(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String text = sdf.format(date);

        return text;

    }

}
