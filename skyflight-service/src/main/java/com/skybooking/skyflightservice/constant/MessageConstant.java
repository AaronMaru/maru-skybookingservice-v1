package com.skybooking.skyflightservice.constant;

import org.springframework.stereotype.Component;

@Component
public class MessageConstant {

    public static final String BOOKING_SUCCESS = "bk_succ";

    public static final String BAD_REQUEST = "bad_req";
    public static final String BOOKING_FAIL = "bk_fail";
    public static final String PRICE_CHANGED = "bk_price_changed";
    public static final String UNAVAILABLE_SEATS = "unavailable_seats";

    public static final String INTERNAL_SERVER_ERROR = "sth_w_w";

}
