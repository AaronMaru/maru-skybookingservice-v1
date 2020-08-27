package com.skybooking.skyflightservice.v1_0_0.util.email;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.ShareFlightRQ;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class EmailBean {

    @Autowired
    Environment environment;

    @Autowired
    private Configuration configuration;

    public void email(Map<String, Object> mailTemplateData) {

        Map<String, String> mailProperty = new HashMap<>();

        mailProperty.put("SMTP_SERVER_HOST", environment.getProperty("spring.email.host"));
        mailProperty.put("SMTP_SERVER_PORT", environment.getProperty("spring.email.port"));
        mailProperty.put("SUBJECT", "Skybooking");
        mailProperty.put("SMTP_USER_NAME", environment.getProperty("spring.email.username"));
        mailProperty.put("SMTP_USER_PASSWORD", environment.getProperty("spring.email.password"));
        mailProperty.put("FROM_USER_EMAIL", environment.getProperty("spring.email.from-address"));
        mailProperty.put("FROM_USER_FULLNAME", environment.getProperty("spring.email.from-name"));

        ShareFlightRQ shareFlightRQ = (ShareFlightRQ) mailTemplateData.get("requestDate");

        mailProperty.put("TO", shareFlightRQ.getEmailsTo());

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendMail(configuration, mailProperty, mailTemplateData);

    }
}
