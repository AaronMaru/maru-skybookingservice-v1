package com.skybooking.stakeholderservice.v1_0_0.util.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Smtp2go {

        public static void sendMail2Go(String sender) {

            final String username = "test-api";
            final String password = "ZzRnc3dpNGk3MnIw";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "mail.smtp2go.com");
            props.put("mail.smtp.port", "2525"); // 8025, 587 and 25 can also be used.
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                Multipart mp = new MimeMultipart("alternative");
                BodyPart textmessage = new MimeBodyPart();
                textmessage.setText("It is a text message n");
                BodyPart htmlmessage = new MimeBodyPart();
                htmlmessage.setContent("It is a html message.", "text/html");
                mp.addBodyPart(textmessage);
                mp.addBodyPart(htmlmessage);
                message.setFrom(new InternetAddress("oeunsopheak@skybooking.info"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(sender));
                message.setSubject("Java Mail using SMTP2GO");
                message.setContent(mp);
                Transport.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        }


}
