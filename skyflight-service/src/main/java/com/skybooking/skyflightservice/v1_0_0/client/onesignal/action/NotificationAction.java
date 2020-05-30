package com.skybooking.skyflightservice.v1_0_0.client.onesignal.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NotificationAction {

    @Autowired
    Environment environment;

    public void sendNotification(String notificationData) {
        try {

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            byte[] sendBytes = notificationData.getBytes("UTF-8");
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
