package com.skybooking.staffservice.v1_0_0.util;

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

public class ApiBean {


    @Autowired
    Environment environment;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send email and sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param reciever
     * @Param message
     */
    public Boolean sendEmailSMS(String reciever, String message) {
        boolean validEmail = EmailValidator.getInstance().isValid(reciever);
        if (NumberUtils.isNumber(reciever.replaceAll("[+]", ""))) {
            sms(reciever, message);
            return true;
        } else if (validEmail) {
            email(reciever, message);
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
    public void email(String TO, String MESSAGE) {

        final String SMTP_SERVER_HOST = environment.getProperty("spring.email.host");
        final String SMTP_SERVER_PORT = environment.getProperty("spring.email.port");
        final String SUBJECT = "Skybooking";

        final String SMTP_USER_NAME = environment.getProperty("spring.email.username");
        final String SMTP_USER_PASSWORD = environment.getProperty("spring.email.password");
        final String FROM_USER_EMAIL = environment.getProperty("spring.email.from-address");
        final String FROM_USER_FULLNAME = environment.getProperty("spring.email.from-name");

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendMail(SMTP_SERVER_HOST, SMTP_SERVER_PORT, SMTP_USER_NAME,
                SMTP_USER_PASSWORD, FROM_USER_EMAIL, FROM_USER_FULLNAME, TO, SUBJECT, MESSAGE);

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
