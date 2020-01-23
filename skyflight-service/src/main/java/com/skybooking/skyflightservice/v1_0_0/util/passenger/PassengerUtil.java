package com.skybooking.skyflightservice.v1_0_0.util.passenger;

import com.skybooking.skyflightservice.v1_0_0.util.datetime.DatetimeFormat;

import java.util.Date;

public class PassengerUtil {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger type by passenger birthday
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param birthday
     * @return
     * @throws Exception
     */
    public static String type(Date birthday) {
        int year = DatetimeFormat.age(birthday).getYears();
        String type = "ADT";

        if (year < 12 && year >= 2) {
            type = "CNN";
        } else if (year < 2) {
            type = "INF";
        }

        return type;
    }
}
