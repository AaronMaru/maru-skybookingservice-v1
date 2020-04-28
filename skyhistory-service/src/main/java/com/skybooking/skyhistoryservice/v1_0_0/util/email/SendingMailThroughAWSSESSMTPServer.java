package com.skybooking.skyhistoryservice.v1_0_0.util.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendingMailThroughAWSSESSMTPServer {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SendingMailThroughAWSSESSMTPServer.class);

    public static int noOfQuickServiceThreads = 20;

    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get config from env
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param Map<String, String>
     * @Return Properties
     */
    public Session getConfig() {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);

        return session;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate pdf file
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param configuration
     * @Param pdfData
     */
    private String replaceCode(String html, String target, String code) {
        return html.replace(target, code);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail throw amazon
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param smtpServerHost
     * @Param smtpServerPort
     * @Param smtpUserName
     * @Param smtpUserPassword
     * @Param fromUserEmail
     * @Param fromUserFullName
     * @Param toEmail
     * @Param subject
     * @Param body
     */
    public void sendEmail(Configuration configuration, Map<String, String> mailProperty,
                          Map<String, Object> mailTemplateData, Map<String, Object> pdfData, List<File> fileList) {

        try {

            Session session = getConfig();

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            Template template = configuration.getTemplate("index.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailTemplateData);

            if (mailTemplateData.containsKey("fullName")) {
                html = this.replaceCode(html, "{{FULL_NAME}}", mailTemplateData.get("fullName").toString());
            }

            if (pdfData != null) {
                quickService.submit(new Runnable() {
                    @Override
                    public void run() {
                        fileList.forEach(file -> {
                            try {
                                helper.addAttachment(file.getName(), file);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }

            helper.setTo(mailProperty.get("TO"));
            helper.setText(html, true);
            helper.setSubject(mailProperty.get("SUBJECT"));
            helper.setFrom(mailProperty.get("FROM_USER_EMAIL"));

            Transport transport = session.getTransport();
            transport.connect(mailProperty.get("SMTP_SERVER_HOST"), mailProperty.get("SMTP_USER_NAME"),
                mailProperty.get("SMTP_USER_PASSWORD"));

            quickService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        transport.sendMessage(message, message.getAllRecipients());
                        fileList.forEach(file -> file.delete());
                    } catch (Exception e) {
                        logger.error("Exception occur while send a mail : ", e);
                    }
                }
            });

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}
