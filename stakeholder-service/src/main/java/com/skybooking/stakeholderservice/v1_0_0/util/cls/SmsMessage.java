package com.skybooking.stakeholderservice.v1_0_0.util.cls;

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
    public String sendSMS(String cases, String code) {

        switch (cases) {
            case "send-verify":
                return code + " is your verify code";
            case "success-reset-password":
                return "Password reset successfully";
            case "deactive-account":
                return "Your account deactive success";
            default:
                return "No message";
        }

    }



}
