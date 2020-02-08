package com.skybooking.skyhistoryservice.v1_0_0.util.general;

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
    public String sendSMS(String cases, int code) {

        switch (cases) {
            case "send-verify":
                return code + " is your verify code";
            case "success-reset-password":
                return "Password reset successfully";
            case "deactive-account":
                return "Your account deactive success";
            case "verify-success":
                return "Your account verify success";
            default:
                return "No message";

        }

    }



}
