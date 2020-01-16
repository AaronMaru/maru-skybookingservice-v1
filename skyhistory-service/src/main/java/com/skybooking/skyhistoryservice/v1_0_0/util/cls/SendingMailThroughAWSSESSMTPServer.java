package com.skybooking.skyhistoryservice.v1_0_0.util.cls;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
                         Map<String, Object> mailTemplateData, Map<String, Object> pdfData) {

        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", mailProperty.get("SMTP_SERVER_PORT"));
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            Template template = configuration.getTemplate("index.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailTemplateData);

            if (mailTemplateData.containsKey("fullName")) {
                html = this.replaceCode(html, "{{FULL_NAME}}", mailTemplateData.get("fullName").toString());
            }

            if (pdfData != null) {
                File file = this.pdfMultiLang(configuration, pdfData);
                helper.addAttachment(file.getName(), file);
            }

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

    private File pdfMultiLang(Configuration configuration, Map<String, Object> pdfData) throws IOException, InterruptedException {

        Pdf pdf = new Pdf();

        ClassPathResource resource = new ClassPathResource(pdfData.get("templateName") + ".pdf");
        File file = new File(resource.getPath());

        try {
            Template template = configuration.getTemplate("pdf/" + pdfData.get("templateName") + ".ftl");
            String htmlTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, pdfData);

            pdf.addPageFromString(htmlTemplate);
            pdf.saveAs(pdfData.get("templateName") + ".pdf");
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return file;
    }
}
