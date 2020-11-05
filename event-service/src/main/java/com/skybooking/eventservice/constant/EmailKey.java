package com.skybooking.eventservice.constant;

public interface EmailKey {

    /*mail template key*/
    String SKY_POINT_TOP_UP = "skp_top_up";
    String SKY_POINT_TOP_UP_FAILED = "skp_top_up_failed";
    String SKY_POINT_EARNED = "skp_earn";
    String SKY_POINT_REDEEM = "skp_redeem";
    String SKY_POINT_REFUND = "skp_refund";
    String SKY_POINT_UPGRADE_LEVEL = "skp_upgrade_level";

    /*mail prop key*/
    String SKY_POINT = "Sky Point";
    String SKY_HOTEL = "Sky Hotel";

    /*mail activemq key*/
    String SKY_POINT_EMAIL_TOP_UP = "sky_point_email_top_up";
    String SKY_POINT_EMAIL_EARNED = "sky_point_email_earned";
    String SKY_POINT_EMAIL_REDEEM = "sky_point_email_redeem";
    String SKY_POINT_EMAIL_REFUND = "sky_point_email_refund";


    //Hotel template ftl and key
    String HOTEL_PAYMENT_SUCCESS = "hotel_payment_success";
    String HOTEL_PAYMENT_INFO = "hotel_payment_info";

}
