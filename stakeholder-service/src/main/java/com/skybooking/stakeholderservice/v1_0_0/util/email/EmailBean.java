package com.skybooking.stakeholderservice.v1_0_0.util.email;

import com.skybooking.stakeholderservice.config.TwilioConfig;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.mail.MailScriptLocaleTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.mail.MultiLanguageNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.mail.MultiLanguageTO;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import freemarker.template.Configuration;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.skybooking.stakeholderservice.config.ActiveMQConfig.STAKE_HOLDER_EMAIL;
import static com.skybooking.stakeholderservice.config.ActiveMQConfig.STAKE_HOLDER_SMS;

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
    private LocalizationBean localizationBean;

    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfig.getAccountSID(), twilioConfig.getAuthToken());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send email and sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param reciever
     * @Param message
     */
    public Boolean sendEmailSMS(String message, Map<String, Object> mailTemplateData) {


        boolean validEmail = EmailValidator.getInstance().isValid(mailTemplateData.get("receiver").toString());

        if (NumberUtils.isNumber(mailTemplateData.get("receiver").toString().replaceAll("[+]", ""))) {

            String link = "";
            if (message.equals("send-download-link")) {
                link = mailTemplateData.get("deepLink").toString();
            }

            mailTemplateData.put("message", sendSMS(message, Integer.parseInt(mailTemplateData.get("code").toString()), link));

            jmsTemplate.convertAndSend(STAKE_HOLDER_SMS, mailTemplateData);

            return true;

        } else if (validEmail) {
            jmsTemplate.convertAndSend(STAKE_HOLDER_EMAIL, mailTemplateData);
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


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Message for sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param cases
     * @Param code
     * @Return String
     */
    public String sendSMS(String cases, int code, String link) {

        String msg = "";

        switch (cases) {
            case "send-login":
                msg = code + " " + localizationBean.multiLanguageRes("log_code");
                break;
            case "success-reset-password":
                msg = localizationBean.multiLanguageRes("reset_pwd_succ");
                break;
            case "deactive-account":
                msg = localizationBean.multiLanguageRes("succ_deact");
                break;
            case "login-success":
                msg = localizationBean.multiLanguageRes("log_succ");
                break;
            case "send-download-link":
                msg = localizationBean.multiLanguageRes("send_dwn_link") + " " + link;
                break;
            default:
                msg = "No message";
                break;
        }

        return msg;

    }

}
