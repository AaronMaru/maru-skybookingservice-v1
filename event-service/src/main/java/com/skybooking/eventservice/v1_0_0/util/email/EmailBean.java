package com.skybooking.eventservice.v1_0_0.util.email;

import com.skybooking.eventservice.v1_0_0.io.nativeQuery.mail.MailScriptLocaleTO;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.mail.MultiLanguageNQ;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.mail.MultiLanguageTO;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skybooking.eventservice.constant.EmailKey.SKY_POINT;


@SuppressWarnings("JavaDoc")
public class EmailBean {

    @Autowired
    private Environment environment;

    @Autowired
    private Configuration configuration;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private MultiLanguageNQ multiLanguageNQ;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param mailTemplateData
     */
    public void email(Map<String, Object> mailTemplateData, List<File> fileList) {

        Map<String, String> mailProperty = new HashMap<>();

        mailProperty.put("SERVER_HOST", environment.getProperty("spring.email.host"));
        mailProperty.put("SERVER_PORT", environment.getProperty("spring.email.port"));
        mailProperty.put("SUBJECT", SKY_POINT);
        mailProperty.put("USER_NAME", environment.getProperty("spring.email.username"));
        mailProperty.put("USER_PASSWORD", environment.getProperty("spring.email.password"));
        mailProperty.put("FROM_USER_EMAIL", environment.getProperty("spring.email.from-address"));

        mailProperty.put("TO", (String) mailTemplateData.get("receiver"));

        mailTemplateData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendMail(configuration, mailProperty, mailTemplateData, fileList);

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
    public Map<String, Object> mailData(String receiver, String name, BigDecimal amount, String template) {

        MultiLanguageTO multiLanguageTO = this.getMailData(template);

        Map<String, Object> mailTemplateData = new HashMap<>();
        mailTemplateData.put("receiver", receiver);
        mailTemplateData.put("fullName", name);
        mailTemplateData.put("amount", amount);
        mailTemplateData.put("templateName", template);
        mailTemplateData.put("script", multiLanguageTO);

        return mailTemplateData;
    }

    public MultiLanguageTO getMailData(String keyword) {

        long localeID = headerBean.getLocalizationId();

        MailScriptLocaleTO exist = multiLanguageNQ.mailScriptLocale(localeID, keyword);

        localeID = (exist == null) ? 1 : localeID;

        return multiLanguageNQ.mailMultiLanguage(localeID, keyword);

    }
}
