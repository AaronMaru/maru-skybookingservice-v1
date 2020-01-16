package com.skybooking.staffservice.v1_0_0.util.general;

import com.skybooking.staffservice.v1_0_0.util.cls.SendingMailThroughAWSSESSMTPServer;
import freemarker.template.Configuration;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ApiBean {


    @Autowired
    Environment environment;

    @Autowired
    private Configuration configuration;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send email and sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param reciever
     * @Param message
     */
    public Boolean sendEmailSMS(String reciever, String message, Map<String, Object> mailTemplateData) {
        boolean validEmail = EmailValidator.getInstance().isValid(reciever);
        if (NumberUtils.isNumber(reciever.replaceAll("[+]", ""))) {
            sms(reciever, message);
            return true;
        } else if (validEmail) {
            email(reciever, mailTemplateData);
            return true;
        }
        return false;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param TO
     * @Param MESSAGE
     */
    public void email(String TO,  Map<String, Object> mailTemplateData) {

        Map<String, String> mailProperty = new HashMap<>();
        mailProperty.put("SMTP_SERVER_HOST", environment.getProperty("spring.email.host"));
        mailProperty.put("SMTP_SERVER_PORT", environment.getProperty("spring.email.port"));
        mailProperty.put("SUBJECT", "Skybooking");
        mailProperty.put("SMTP_USER_NAME", environment.getProperty("spring.email.username"));
        mailProperty.put("SMTP_USER_PASSWORD", environment.getProperty("spring.email.password"));
        mailProperty.put("FROM_USER_EMAIL", environment.getProperty("spring.email.from-address"));
        mailProperty.put("FROM_USER_FULLNAME", environment.getProperty("spring.email.from-name"));
        mailProperty.put("TO", TO);

        mailTemplateData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendMail(configuration, mailProperty, mailTemplateData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param TO
     * @Param MESSAGE
     */
    public void sms(String TO, String MESSAGE) {
        RestTemplate restAPi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", environment.getProperty("spring.sms.username"));
        map.add("pass", environment.getProperty("spring.sms.pass"));
        map.add("sender", environment.getProperty("spring.sms.sender"));
        map.add("cd", environment.getProperty("spring.sms.cd"));
        map.add("smstext", MESSAGE);
        map.add("isflash", environment.getProperty("spring.sms.isflash"));
        map.add("gsm", TO);
        map.add("int", environment.getProperty("spring.sms.int"));

        HttpEntity<MultiValueMap<String, String>> requestSMS =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);
        restAPi.exchange(environment.getProperty("spring.sms.url"), HttpMethod.POST, requestSMS, String.class).getBody();
    }


}
