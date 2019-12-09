package com.skybooking.staffservice.v1_0_0.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendingMailThroughAWSSESSMTPServer {

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
    public void sendMail(Configuration configuration, Map<String, String> mailProperty,
                         Map<String, String> mailTemplateData) {

        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", mailProperty.get("SMTP_SERVER_PORT"));
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message);

            Template template = configuration.getTemplate("index.ftl");

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailTemplateData);

            helper.setTo(mailProperty.get("TO"));
            helper.setText(html, true);
            helper.setSubject(mailProperty.get("SUBJECT"));
            helper.setFrom(mailProperty.get("FROM_USER_EMAIL"));

            Transport transport = session.getTransport();
            transport.connect(mailProperty.get("SMTP_SERVER_HOST"), mailProperty.get("SMTP_USER_NAME"),
                    mailProperty.get("SMTP_USER_PASSWORD"));
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

}
