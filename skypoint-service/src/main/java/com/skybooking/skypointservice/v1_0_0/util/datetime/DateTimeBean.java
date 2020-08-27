package com.skybooking.skypointservice.v1_0_0.util.datetime;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateTimeBean {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Date to iso date
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param date
     */
    public String convertDateTime(Date date) {

        var zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")).replace("Z", "+00:00");

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * convert ElapsedTime to hour and minute
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param minute
     */
    public String convertNumberToHour(Integer minute) {

        String duration = minute > 60 ? minute / 60 + "h" + minute % 60 + "m" : minute + "m";

        if (minute % 60 == 0 && minute > 60) {
            duration = minute / 60 + "h";
        }

        return duration;
    }
}
