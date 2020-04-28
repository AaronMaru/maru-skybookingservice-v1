package com.skybooking.stakeholderservice.v1_0_0.util.passenger;


import com.skybooking.stakeholderservice.v1_0_0.util.datetime.DateTimeBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Passenger {

    @Autowired
    private DateTimeBean dateTimeBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger type by passenger birthday
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param birthday
     * @return
     * @throws Exception
     */
    public String passengerType(Date birthday) {

        int year = dateTimeBean.age(birthday).getYears();
        String type = "ADT";

        if (year < 12 && year >= 2) {
            type = "CNN";
        } else if (year < 2) {
            type = "INF";
        }

        return type;
    }


//    /**
//     * -----------------------------------------------------------------------------------------------------------------
//     * get passenger type for insert into database
//     * -----------------------------------------------------------------------------------------------------------------
//     *
//     * @param type
//     * @return String
//     */
//    public String getPassengerType(String type) {
//
//        var passengerType = switch (type.toUpperCase()) {
//            case PassengerCodeConstant.ADULT -> "Adult";
//            case PassengerCodeConstant.CHILD -> "Child";
//            case PassengerCodeConstant.INFANT -> "Infant";
//            default -> throw new IllegalArgumentException("Invalid passenger type: " + type);
//        };
//
//
//        return passengerType;
//
//    }

}
