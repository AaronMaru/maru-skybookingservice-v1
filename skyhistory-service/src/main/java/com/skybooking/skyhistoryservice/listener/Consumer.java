package com.skybooking.skyhistoryservice.listener;

import com.skybooking.skyhistoryservice.v1_0_0.util.email.EmailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.skybooking.skyhistoryservice.config.ActiveMQConfig.*;

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


    @JmsListener(destination = SKY_HISTORY_EMAIL)
    public void email(Map<String, Object> mailTemplateData) {
        emailBean.email(mailTemplateData);
    }

    @JmsListener(destination = SKY_HISTORY_SMS)
    public void sms(Map<String, Object> mailTemplateData) {
        emailBean.sms(mailTemplateData);
    }

}
