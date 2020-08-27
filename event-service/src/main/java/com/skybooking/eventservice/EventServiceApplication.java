package com.skybooking.eventservice;

import com.skybooking.eventservice.v1_0_0.util.email.EmailBean;
import com.skybooking.eventservice.v1_0_0.util.email.SendingMailThroughAWSSESSMTPServer;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.eventservice.v1_0_0.util.pdf.PdfBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    EmailBean emailBean() {
        return new EmailBean();
    }

    @Bean
    HeaderBean headerBean() {
        return new HeaderBean();
    }

    @Bean
    SendingMailThroughAWSSESSMTPServer mail() {
        return new SendingMailThroughAWSSESSMTPServer();
    }

    @Bean
    PdfBean pdfBean() {
        return new PdfBean();
    }
}
