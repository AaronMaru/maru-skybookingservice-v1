package com.skybooking.skyhistoryservice.v1_0_0.util.cls.notification;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PushNotificationOptions {

    public static final String API_KEY = "ZWY3ODM2M2MtMDY1YS00NTBlLTllODktY2ZiY2JkMDZmYmZk";
    public static final String APP_ID = "7e6328c0-0512-4250-805d-21964a7f0f36";

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {

        String jsonResponse;
        if (httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(),"UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }

    public static void sendMessageToUsers(String userId, String message) {

        //Player id example: e21291a0-749c-4f45-b9b5-5f86a6eb0cbd
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            String strJsonBody = jsonToUser(message, userId, con);
//            String strJsonBody = jsonToAllUser(message, userId, con);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
//            jsonResponse = mountResponseRequest(con, httpResponse);
//            System.out.println(jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public static String jsonToUser(String message, String userId, HttpURLConnection con) {

        con.setRequestProperty("Authorization", API_KEY);
        String strJsonBody = "{"
                +   "\"app_id\": \""+ APP_ID +"\","
                +   "\"include_player_ids\": [\""+ userId +"\"],"
                +   "\"data\": {\"foo\": \"bar\"},"
                +   "\"contents\": {\"en\": \""+ message +"\"}"
                + "}";
        return strJsonBody;

    }

    public static String jsonToAllUser(String message, String userId, HttpURLConnection con) {

        con.setRequestProperty("Authorization", "Basic "+ API_KEY);
        String strJsonBody = "{"
                    +   "\"app_id\": \""+ APP_ID +"\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \""+ message +"\"}"
                    + "}";
        return strJsonBody;

    }

}
