package com.skybooking.skyhistoryservice.v1_0_0.service.implment.booking;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.SendBookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.util.cls.Duplicate;
import com.skybooking.skyhistoryservice.v1_0_0.util.cls.SmsMessage;
import com.skybooking.skyhistoryservice.v1_0_0.util.general.ApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendBookingIP implements SendBookingSV {


    @Autowired
    private ApiBean apiBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail receipt
     * -----------------------------------------------------------------------------------------------------------------
     *
     */
    public void sendReceipt() {
        SmsMessage sms = new SmsMessage();
        //the static mail will replace by Reqeust body
        apiBean.sendEmailSMS("tenglyheang@skybooking.info", sms.sendSMS("send-verify", 0),
                Duplicate.mailTemplateData("","booking-info"),
                Duplicate.pdfData("", "receipt"));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail itinerary
     * -----------------------------------------------------------------------------------------------------------------
     *
     */
    public void sendItinerary() {
        SmsMessage sms = new SmsMessage();
        //the static mail will replace by Reqeust body
        apiBean.sendEmailSMS("tenglyheang@skybooking.info", sms.sendSMS("send-verify", 0),
                Duplicate.mailTemplateData("","booking-info"),
                Duplicate.pdfData("","itinerary"));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     *
     */
    public void sendPayment() {
        SmsMessage sms = new SmsMessage();
        apiBean.sendEmailSMS("tenglyheang@skybooking.info", sms.sendSMS("send-payment", 0),
                Duplicate.mailTemplateData("","payment-success"), null);
    }



}
