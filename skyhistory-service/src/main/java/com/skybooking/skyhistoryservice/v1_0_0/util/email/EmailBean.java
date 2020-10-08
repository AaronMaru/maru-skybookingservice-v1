package com.skybooking.skyhistoryservice.v1_0_0.util.email;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.skybooking.skyhistoryservice.config.TwilioConfig;
import com.skybooking.skyhistoryservice.listener.Consumer;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking.BookingEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.locale.TranslationEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MailScriptLocaleTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MultiLanguageNQ;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.mail.MultiLanguageTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.locale.TranslationRP;
import com.skybooking.skyhistoryservice.v1_0_0.util.general.ApiBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skybooking.skyhistoryservice.config.ActiveMQConfig.SKY_HISTORY_EMAIL;
import static com.skybooking.skyhistoryservice.config.ActiveMQConfig.SKY_HISTORY_SMS;

public class EmailBean {

    private Logger logger = LoggerFactory.getLogger(EmailBean.class);

    @Autowired
    private Environment environment;

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

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private BookingRP bookingRP;

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
    public Boolean sendEmailSMS(String message, Map<String, Object> mailTemplateData, Map<String, Object> pdfData) {

        mailTemplateData.put("pdfData", pdfData);

        boolean validEmail = EmailValidator.getInstance().isValid(mailTemplateData.get("receiver").toString());
        if (NumberUtils.isNumber(mailTemplateData.get("receiver").toString().replaceAll("[+]", ""))) {
            mailTemplateData.put("message", "No message");
            jmsTemplate.convertAndSend(SKY_HISTORY_SMS, mailTemplateData);
            return true;
        } else if (validEmail) {
            jmsTemplate.convertAndSend(SKY_HISTORY_EMAIL, mailTemplateData);
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
            pdfData.put("pdfLang", mailTemplateData.get("pdfLang"));
        }

        List<String> sendData = null;
        if (pdfData != null) {
            sendData = (List<String>) pdfData.get("sendData");
        }

        Map<String, Object> data = (Map<String, Object>) mailTemplateData.get("data");
        Map<String, Object> bookingInfo = (Map<String, Object>) data.get("bookingInfo");

        String bookingCode = (String) bookingInfo.get("bookingCode");

        List<File> fileList = new ArrayList<>();
        if (sendData != null) {

            sendData.forEach(item -> {
                try {
                    File file = pdfRender(configuration, pdfData, item);

                    this.uploadItineraryReceiptTos3bucket(file, item, bookingCode);

                    fileList.add(file);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendEmail(configuration, mailProperty, mailTemplateData, pdfData, fileList);
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
     * Template mail data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param fullName
     * @Param code
     * @Return template
     */
    public Map<String, Object> mailData(String receiver, String name, int code, String script, String template,
                                        String label) {

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

    public File pdfRender(Configuration configuration, Map<String, Object> pdfData, String keyword)
            throws IOException, InterruptedException {
        Map<String, Object> receiptMap = (Map<String, Object>) pdfData.get("label_" + keyword);
        receiptMap.put("data", pdfData.get("data_" + keyword));
        receiptMap.put("pdfLang", pdfData.get("pdfLang"));

        if (keyword.equals("itinerary")) {
            receiptMap.put("logoPdf", pdfData.get("logoPdf"));
        }
        Pdf pdf = new Pdf();
        pdf.addParam(new Param("--encoding", "UTF-8"));
        String fileName = receiptMap.get("templateName") + "-" + new Timestamp(System.currentTimeMillis()).getTime() + ".pdf";
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = new File(resource.getPath());

        try {
            Template template = configuration.getTemplate("pdf/" + receiptMap.get("templateName") + ".ftl");
            String htmlTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, receiptMap);

            pdf.addPageFromString(htmlTemplate);

            pdf.saveAs(fileName);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Upload file to amazon
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param filename
     * @Param file
     */
//    @Async
    public void uploadItineraryReceiptTos3bucket(File file, String item, String bookingCode) {

        BookingEntity bookingEntity = bookingRP.findByBookingCode(bookingCode);

        LocalDateTime now = LocalDateTime.now();
        String yearMonth = now.getYear() + "/" + now.getMonthValue();
        String path = item + "/" + (bookingEntity.getRecieptPath() != null ? bookingEntity.getRecieptPath() : yearMonth);

        String generateFileName = apiBean.generateFileName(null) + ".pdf";

        if (bookingEntity.getRecieptFile() != null && item.equals("receipt")) {
            generateFileName = bookingEntity.getRecieptFile();
        }
        if (bookingEntity.getItineraryFile() != null && item.equals("itinerary")) {
            generateFileName = bookingEntity.getItineraryFile();
        }

        try {
            s3client.putObject(new PutObjectRequest(environment.getProperty("spring.awsImage.bucket")
                    + environment.getProperty("spring.awsImage.pdfUrl") + path, generateFileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            logger.error("Amazon Upload fail " + e);
        }

        if (bookingEntity.getItineraryFile() == null && item.equals("itinerary")) {
            bookingEntity.setItineraryFile(generateFileName);
            bookingEntity.setItineraryPath(yearMonth);
            bookingRP.save(bookingEntity);
        }

        if (bookingEntity.getRecieptFile() == null && item.equals("receipt")) {
            bookingEntity.setRecieptFile(generateFileName);
            bookingEntity.setRecieptPath(yearMonth);
            bookingRP.save(bookingEntity);
        }

    }
}
