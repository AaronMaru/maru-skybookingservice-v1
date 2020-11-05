package com.skybooking.eventservice.v1_0_0.util.sms;

import com.skybooking.eventservice.config.TwilioConfig;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.sms.SMSMultiLanguageNQ;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.sms.SMSMultiLanguageTO;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class SmsBean {
    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private SMSMultiLanguageNQ smsMultiLanguageNQ;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSID(), twilioConfig.getAuthToken());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param data Map
     */
    public void sendSms(Map<String, Object> data) {
        PhoneNumber receiver = new PhoneNumber(data.get("receiver").toString());
        PhoneNumber sender = new PhoneNumber(twilioConfig.getPhoneNumber());
        String body = smsMessage(data);
        Message.creator(receiver, sender, body).create();
    }

    public SMSMultiLanguageTO getSmsScript(String keyword) {

        long localeID = headerBean.getLocalizationId();

        SMSMultiLanguageTO exist = smsMultiLanguageNQ.smsMultiLanguage(localeID, keyword);
        localeID = (exist == null) ? 1 : localeID;

        return smsMultiLanguageNQ.smsMultiLanguage(localeID, keyword);

    }

    public Map<String, Object> smsData(String receiver, String template) {
        Map<String, Object> smsData = new HashMap<>();
        smsData.put("receiver", receiver);
        smsData.put("template", template);
        return smsData;
    }

    public String smsMessage(Map<String, Object> smsData) {

        String body = this.getSmsScript(smsData.get("template").toString()).getSubject();

        if (smsData.get("bookingId") != null) {
            body = body.replace("{{BOOKING_ID}}", smsData.get("bookingId").toString());
        }

        if (smsData.get("amount") != null) {
            body = body.replace("{{AMOUNT}}", smsData.get("amount").toString());
        }

        if (smsData.get("hotel") != null) {
            body = body.replace("{{TRANSACTION_FOR}}", smsData.get("hotel").toString());
        }

        if (smsData.get("flight") != null) {
            body = body.replace("{{TRANSACTION_FOR}}", smsData.get("flight").toString());
        }

        if (smsData.get("level") != null) {
            body = body.replace("{{NEW_LEVEL}}", smsData.get("level").toString());
        }

        return body;
    }


}
