package com.skybooking.eventservice.v1_0_0.util.email;

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

import static com.skybooking.eventservice.constant.PropKey.*;

@SuppressWarnings("JavaDoc")
public class SendingMailThroughAWSSESSMTPServer {

    public static int noOfQuickServiceThreads = 20;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SendingMailThroughAWSSESSMTPServer.class);
    private final ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail throw amazon
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param configuration
     * @Param mailTemplateData
     * @Param mailProperty
     */
    public void sendMail(Configuration configuration, Map<String, String> mailProperty,
                         Map<String, Object> mailTemplateData, List<File> fileList) {
        try {
            Properties props = System.getProperties();
            props.put(MTP, SMTP);
            props.put(MSP, mailProperty.get("SERVER_PORT"));
            props.put(MSSE, TRUE);
            props.put(MSA, TRUE);

            Session session = Session.getDefaultInstance(props);

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = configuration.getTemplate("index.ftl");

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailTemplateData);

            if (mailTemplateData.get("fullName") != null) {
                html = this.replaceCode(html, "{{FULL_NAME}}", mailTemplateData.get("fullName").toString());
            }

            if (mailTemplateData.get("transactionId") != null) {
                html = this.replaceCode(html, "{{TRANSACTION_ID}}",
                        mailTemplateData.get("transactionId").toString());
            }

            if (mailTemplateData.get("amount") != null) {
                html = this.replaceCode(html, "{{AMOUNT}}", mailTemplateData.get("amount").toString());
            }

            if (mailTemplateData.get("earnAmount") != null) {
                html = this.replaceCode(html, "{{EARN_AMOUNT}}", mailTemplateData.get("earnAmount").toString());
            }

            if (fileList != null) {
                quickService.submit(() -> fileList.forEach(file -> {
                    try {
                        helper.addAttachment(file.getName(), file);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }));
            }
            helper.setTo(mailProperty.get("TO"));
            helper.setText(html, true);
            helper.setSubject(mailProperty.get("SUBJECT"));
            helper.setFrom(mailProperty.get("FROM_USER_EMAIL"));

            Transport transport = session.getTransport();
            transport.connect(mailProperty.get("SERVER_HOST"), mailProperty.get("USER_NAME"),
                    mailProperty.get("USER_PASSWORD"));


            quickService.submit(() -> {
                try {
                    transport.sendMessage(message, message.getAllRecipients());
                    if (fileList != null) {
                        fileList.forEach(File::delete);
                    }
                } catch (Exception e) {
                    logger.error("Exception occur while send a mail : ", e);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private String replaceCode(String html, String target, String code) {
        return html.replace(target, code);
    }
}
