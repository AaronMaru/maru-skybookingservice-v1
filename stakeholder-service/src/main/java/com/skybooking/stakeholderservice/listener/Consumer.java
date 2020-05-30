package com.skybooking.stakeholderservice.listener;

import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import com.skybooking.stakeholderservice.v1_0_0.util.notification.PushNotificationOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.skybooking.stakeholderservice.config.ActiveMQConfig.*;


@Component
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private EmailBean email;

    @Autowired
    private PushNotificationOptions notificationOptions;

    @JmsListener(destination = STAKE_HOLDER_EMAIL)
    public void email(Map<String, Object> mailTemplateData) {
        email.email(mailTemplateData);
    }

    @JmsListener(destination = STAKE_HOLDER_SMS)
    public void sms(Map<String, Object> mailTemplateData) {
        email.sms(mailTemplateData);
    }

    @JmsListener(destination = NOTIFICATION)
    public void notification(HashMap<String, Object> scriptData) {
        notificationOptions.executeByQueue(scriptData);
    }

}
