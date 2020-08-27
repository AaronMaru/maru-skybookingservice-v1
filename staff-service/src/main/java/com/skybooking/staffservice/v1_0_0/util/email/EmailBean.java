package com.skybooking.staffservice.v1_0_0.util.email;

import com.skybooking.staffservice.config.TwilioConfig;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.mail.MailScriptLocaleTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.mail.MultiLanguageNQ;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.mail.MultiLanguageTO;
import com.skybooking.staffservice.v1_0_0.util.header.HeaderBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import freemarker.template.Configuration;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.skybooking.staffservice.config.ActiveMQConfig.EMAIL;
import static com.skybooking.staffservice.config.ActiveMQConfig.SMS;

public class EmailBean {

    @Autowired
    private Environment environment;

    @Autowired
    private Configuration configuration;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private MultiLanguageNQ multiLanguageNQ;

    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSID(), twilioConfig.getAuthToken());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send email and sms
     * ---------------------------------------------------------------  --------------------------------------------------
     *
     * @Param reciever
     * @Param message
     */
    public Boolean sendEmailSMS(String message, Map<String, Object> mailTemplateData) {

        boolean validEmail = EmailValidator.getInstance().isValid(mailTemplateData.get("receiver").toString());
        if (NumberUtils.isNumber(mailTemplateData.get("receiver").toString().replaceAll("[+]", ""))) {
            mailTemplateData.put("message", "Hello");
            jmsTemplate.convertAndSend(SMS, mailTemplateData);
            return true;
        } else if (validEmail) {
            jmsTemplate.convertAndSend(EMAIL, mailTemplateData);
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
    public void email(Map<String, Object> mailTemplateData) {

        Map<String, String> mailProperty = new HashMap<>();

        mailProperty.put("SMTP_SERVER_HOST", environment.getProperty("spring.email.host"));
        mailProperty.put("SMTP_SERVER_PORT", environment.getProperty("spring.email.port"));
        mailProperty.put("SUBJECT", "Skybooking");
        mailProperty.put("SMTP_USER_NAME", environment.getProperty("spring.email.username"));
        mailProperty.put("SMTP_USER_PASSWORD", environment.getProperty("spring.email.password"));
        mailProperty.put("FROM_USER_EMAIL", environment.getProperty("spring.email.from-address"));
        mailProperty.put("FROM_USER_FULLNAME", environment.getProperty("spring.email.from-name"));

        mailProperty.put("TO", (String) mailTemplateData.get("receiver"));

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
    public void sms(Map<String, Object> data) {

        PhoneNumber receiver = new PhoneNumber("+" + data.get("receiver").toString());

        PhoneNumber sender = new PhoneNumber(twilioConfig.getPhoneNumber());

        String body = data.get("message").toString();

        Message message = Message.creator(receiver, sender, body).create();

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Template mail data and SMS
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param fullName
     * @Param code
     * @Return template
     */
    public Map<String, Object> mailData(String receiver, String name, int code, String template) {

        MultiLanguageTO multiLanguageTO = this.getMailData(template);

        Map<String, Object> mailTemplateData = new HashMap<>();
        mailTemplateData.put("receiver", receiver);
        mailTemplateData.put("fullName", name);
        mailTemplateData.put("code", Integer.toString(code));
        mailTemplateData.put("templateName", template);
        mailTemplateData.put("script", multiLanguageTO);

        return mailTemplateData;
    }


    public MultiLanguageTO getMailData(String keyword) {

        Long localeID = headerBean.getLocalizationId();

        MailScriptLocaleTO exist = multiLanguageNQ.mailScriptLocale(localeID, keyword);

        localeID = (exist == null) ? 1 : localeID;

        MultiLanguageTO multiLanguageTO = multiLanguageNQ.mailMultiLanguage(localeID, keyword);

        return multiLanguageTO;

    }

}
