package com.skybooking.stakeholderservice.v1_0_0.util.general;

public class SmsMessage {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Message for sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param cases
     * @Param code
     * @Return String
     */
    public String sendSMS(String cases, int code, String link) {

        switch (cases) {
            case "send-login":
                return code + " is your login code";
            case "success-reset-password":
                return "Password reset successfully";
            case "deactive-account":
                return "Your account deactive success";
            case "login-success":
                return "Your account login success";
            case "send-download-link":
                return "Welcome to skybooking download the skybooking!! app and enjoy your travel and book cheap flight ticket click " + link;
            default:
                return "No message";

        }

    }


}
