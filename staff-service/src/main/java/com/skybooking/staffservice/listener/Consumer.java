package com.skybooking.staffservice.listener;

import com.skybooking.staffservice.v1_0_0.util.email.EmailBean;
import com.skybooking.staffservice.v1_0_0.util.general.ApiBean;
import com.skybooking.staffservice.v1_0_0.util.notification.PushNotificationOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.skybooking.staffservice.config.ActiveMQConfig.*;

/**
 * Created by : maru
 * Date  : 1/22/2020
 * Time  : 4:47 PM
 */

@Component
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private PushNotificationOptions notificationOptions;

    @JmsListener(destination = EMAIL)
    public void email(Map<String, Object> mailTemplateData) {
        emailBean.email(mailTemplateData);
    }

    @JmsListener(destination = SMS)
    public void sms(Map<String, Object> mailTemplateData) {
        emailBean.sms(mailTemplateData);
    }

    @JmsListener(destination = NOTIFICATION)
    public void notification(HashMap<String, Object> scriptData) {
        notificationOptions.executeByQueue(scriptData);
    }

}
