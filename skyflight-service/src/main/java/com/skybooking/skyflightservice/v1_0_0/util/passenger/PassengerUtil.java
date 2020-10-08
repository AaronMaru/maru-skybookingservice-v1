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

    public static String gender(String gender) {
        String passengerGender;
        switch (gender) {
            case "male":
                passengerGender = "M";
                break;
            case "female":
                passengerGender = "F";
                break;
            case "MALE":
                passengerGender = "M";
                break;
            case "FEMALE":
                passengerGender = "F";
                break;
            default:
                throw new IllegalArgumentException("Invalid passenger gender: " + gender);
        }

        return passengerGender;
    }

    public static String idType(int type) {
        String idType = "";
        switch (type) {
            case 0:
                idType = "PASSPORT";
                break;
            case 1:
                idType = "ID_CARD";
                break;
            default:
                throw new IllegalArgumentException("Invalid ID type: " + idType);
        }

        return idType;
    }
}
