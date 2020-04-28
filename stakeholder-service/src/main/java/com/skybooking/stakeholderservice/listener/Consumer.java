package com.skybooking.stakeholderservice.listener;

import com.skybooking.stakeholderservice.v1_0_0.util.email.EmailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.skybooking.stakeholderservice.config.ActiveMQConfig.STAKE_HOLDER_EMAIL;
import static com.skybooking.stakeholderservice.config.ActiveMQConfig.STAKE_HOLDER_SMS;

/**
 * Created by : maru
 * Date  : 1/22/2020
 * Time  : 4:47 PM
 */

@Component
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(Consumer.class);


    @Autowired
    private EmailBean email;


    @JmsListener(destination = STAKE_HOLDER_EMAIL)
    public void email(Map<String, Object> mailTemplateData) {
        email.email(mailTemplateData);
    }

    @JmsListener(destination = STAKE_HOLDER_SMS)
    public void sms(Map<String, Object> mailTemplateData) {
        email.sms(mailTemplateData);
    }

}
