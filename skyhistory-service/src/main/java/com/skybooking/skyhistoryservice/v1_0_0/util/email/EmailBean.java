package com.skybooking.skyhistoryservice.v1_0_0.util.email;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.locale.TranslationEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MailScriptLocaleTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MultiLanguageNQ;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MultiLanguageTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.locale.TranslationRP;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skybooking.skyhistoryservice.config.ActiveMQConfig.*;

public class EmailBean {

    @Autowired
    Environment environment;

    @Autowired
    private Configuration configuration;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private LocaleRP localeRP;

    @Autowired
    private MultiLanguageNQ multiLanguageNQ;

    @Autowired
    private TranslationRP translationRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send email and sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param reciever
     * @Param message
     */
    public Boolean sendEmailSMS(String message, Map<String, Object> mailTemplateData, Map<String, Object> pdfData) {

        mailTemplateData.put("pdfData", pdfData);

        boolean validEmail = EmailValidator.getInstance().isValid(mailTemplateData.get("receiver").toString());
        if (NumberUtils.isNumber(mailTemplateData.get("receiver").toString().replaceAll("[+]", ""))) {
            mailTemplateData.put("message", "No message");
            jmsTemplate.convertAndSend(SMS, mailTemplateData);
            return true;
        } else if (validEmail) {
//            email(mailTemplateData);
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

        Map<String, Object> pdfData = (Map<String, Object>) mailTemplateData.get("pdfData");

        Map<String, String> mailProperty = new HashMap<>();
        mailProperty.put("SMTP_SERVER_HOST", environment.getProperty("spring.email.host"));
        mailProperty.put("SMTP_SERVER_PORT", environment.getProperty("spring.email.port"));
        mailProperty.put("SUBJECT", "Skybooking");
        mailProperty.put("SMTP_USER_NAME", environment.getProperty("spring.email.username"));
        mailProperty.put("SMTP_USER_PASSWORD", environment.getProperty("spring.email.password"));
        mailProperty.put("FROM_USER_EMAIL", environment.getProperty("spring.email.from-address"));
        mailProperty.put("FROM_USER_FULLNAME", environment.getProperty("spring.email.from-name"));
        mailProperty.put("TO", mailTemplateData.get("receiver").toString());

        mailTemplateData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));

        if (pdfData != null) {
            pdfData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));
        }

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendMail(configuration, mailProperty, mailTemplateData, pdfData);

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

        RestTemplate restAPi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", environment.getProperty("spring.sms.username"));
        map.add("pass", environment.getProperty("spring.sms.pass"));
        map.add("sender", environment.getProperty("spring.sms.sender"));
        map.add("cd", environment.getProperty("spring.sms.cd"));
        map.add("smstext", data.get("message").toString());
        map.add("isflash", environment.getProperty("spring.sms.isflash"));
        map.add("gsm", data.get("receiver").toString());
        map.add("int", environment.getProperty("spring.sms.int"));

        HttpEntity<MultiValueMap<String, String>> requestSMS = new HttpEntity<>(map, headers);
        restAPi.exchange(environment.getProperty("spring.sms.url"), HttpMethod.POST, requestSMS, String.class)
                .getBody();

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Template mail data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param fullName
     * @Param code
     * @Return template
     */
    public Map<String, Object> mailData(String receiver, String name, int code, String script, String template, String label) {

        MultiLanguageTO multiLanguageTO = this.getMailData(script);

        Map<String, Object> mailTemplateData = new HashMap<>();
        mailTemplateData.put("receiver", receiver);
        mailTemplateData.put("fullName", name);
        mailTemplateData.put("code", Integer.toString(code));
        mailTemplateData.put("templateName", template);
        mailTemplateData.put("script", multiLanguageTO);

        List<TranslationEntity> labels = this.translationLabel(label);

        labels.forEach(item -> mailTemplateData.put(item.getKey(), item.getValue()));

        return mailTemplateData;
    }


    public MultiLanguageTO getMailData(String keyword) {

        Long localeID = headerBean.getLocalizationId();

        MailScriptLocaleTO exist = multiLanguageNQ.mailScriptLocale(localeID, keyword);

        localeID = (exist == null) ? 1 : localeID;

        MultiLanguageTO multiLanguageTO = multiLanguageNQ.mailMultiLanguage(localeID, keyword);

        return multiLanguageTO;

    }

    private List<TranslationEntity> translationLabel(String label) {

        String locale = headerBean.getLocalization();

        LocaleEntity localeEntity = localeRP.findLocaleByLocale(locale);

        List<TranslationEntity> translationEntities = translationRP.findByModule(localeEntity.getId(), label);

        return translationEntities;
    }

    public Map<String, Object> dataPdfTemplate(String pdfTemplate, String label) {

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("templateName", pdfTemplate);


        List<TranslationEntity> labels = this.translationLabel(label);

        labels.forEach(item -> pdfData.put(item.getKey(), item.getValue()));

        return pdfData;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send email and sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param reciever
     * @Param message
     */
    public Boolean sendReceiptAndItinerary(String message, Map<String, Object> mailTemplateData,
                                           Map<String, Object> pdfData) {

        mailTemplateData.put("pdfData", pdfData);

        boolean validEmail = EmailValidator.getInstance().isValid(mailTemplateData.get("receiver").toString());
        if (NumberUtils.isNumber(mailTemplateData.get("receiver").toString().replaceAll("[+]", ""))) {
            mailTemplateData.put("message", "No message");
            jmsTemplate.convertAndSend(SMS, mailTemplateData);
            return true;
        } else if (validEmail) {
            jmsTemplate.convertAndSend(QUEUE, mailTemplateData);
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
    public void receiptAndItinerary(Map<String, Object> mailTemplateData) {

        Map<String, Object> pdfData = (Map<String, Object>) mailTemplateData.get("pdfData");

        Map<String, String> mailProperty = new HashMap<>();
        mailProperty.put("SMTP_SERVER_HOST", environment.getProperty("spring.email.host"));
        mailProperty.put("SMTP_SERVER_PORT", environment.getProperty("spring.email.port"));
        mailProperty.put("SUBJECT", "Skybooking");
        mailProperty.put("SMTP_USER_NAME", environment.getProperty("spring.email.username"));
        mailProperty.put("SMTP_USER_PASSWORD", environment.getProperty("spring.email.password"));
        mailProperty.put("FROM_USER_EMAIL", environment.getProperty("spring.email.from-address"));
        mailProperty.put("FROM_USER_FULLNAME", environment.getProperty("spring.email.from-name"));
        mailProperty.put("TO", mailTemplateData.get("receiver").toString());

        mailTemplateData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));

        if (pdfData != null) {
            pdfData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));
        }

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendReceiptAndItinerary(configuration, mailProperty, mailTemplateData, pdfData);

    }

}
