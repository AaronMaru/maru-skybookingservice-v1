package com.skybooking.skyhistoryservice.listener;

import com.skybooking.skyhistoryservice.v1_0_0.util.general.ApiBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import java.util.Map;

import static com.skybooking.skyhistoryservice.config.ActiveMQConfig.EMAIL;
import static com.skybooking.skyhistoryservice.config.ActiveMQConfig.SMS;

/**
 * Created by : maru
 * Date  : 1/22/2020
 * Time  : 4:47 PM
 */

@Component
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(Consumer.class);


    @Autowired
    private ApiBean apiBean;


    @JmsListener(destination = EMAIL)
    public void email(Map<String, Object> mailTemplateData) {
        apiBean.email(mailTemplateData);
    }

    @JmsListener(destination = SMS)
    public void sms(Map<String, Object> mailTemplateData) {
        apiBean.sms(mailTemplateData);
    }

}
