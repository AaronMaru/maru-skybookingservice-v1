package com.skybooking.eventservice.v1_0_0.client.onesignal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class NotificationAction {

    @Autowired
    Environment environment;

    public void sendNotification(String notificationData) {
        try {

            URL url = new URL(environment.getProperty("spring.onesignal.url"));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            byte[] sendBytes = notificationData.getBytes(StandardCharsets.UTF_8);
            con.setFixedLengthStreamingMode(sendBytes.length);
            con.setRequestProperty("Authorization", environment.getProperty("spring.onesignal.apiKey"));

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            con.getResponseCode();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
