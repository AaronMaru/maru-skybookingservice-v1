package com.skybooking.staffservice.v1_0_0.util.notification;

import com.skybooking.staffservice.v1_0_0.io.enitity.notification.NotificationEntity;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.staffservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.staffservice.v1_0_0.util.JwtUtils;
import com.skybooking.staffservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PushNotificationOptions {

    @Autowired
    Environment environment;

    @Autowired
    private NotificationNQ notificationNQ;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private NotificationRP notificationRP;

    @Autowired
    private JwtUtils jwtUtils;

    public void sendMessageToUsers(String scriptKey, Integer subjectId, Long skyuserId) {

        try {

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            String strJsonBody = jsonToUser(scriptKey, subjectId ,con, skyuserId);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            con.getResponseCode();

        } catch(Throwable t) {
            t.printStackTrace();
        }

    }

    public String jsonToUser( String scriptKey, Integer bookingId ,HttpURLConnection con, Long skyuserId) {

        ScriptingTO scriptingTO = notificationNQ.scripting( headerBean.getLocalizationId(), scriptKey);

        addNotificationHisitory(bookingId, scriptingTO, skyuserId);

        con.setRequestProperty("Authorization", environment.getProperty("spring.onesignal.apiKey"));
        return  "{"
                +   "\"app_id\": \""+ environment.getProperty("spring.onesignal.appId") +"\","
                +   "\"include_player_ids\": [\""+headerBean.getPlayerId()+"\"],"
                +   "\"data\": {\"urlKey\": \""+scriptKey+"\"},"
                +   "\"contents\": {\"en\": \""+scriptingTO.getSubject()+"\"}"
                + "}";

    }

    public void addNotificationHisitory(Integer subjectId, ScriptingTO scriptingTO, Long skyuserId) {

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setSendScriptId(scriptingTO.getScriptId());
        notificationEntity.setStakeholderUserId( (skyuserId == null) ? jwtUtils.getUserToken().getStakeholderId() : skyuserId );

        Long companyId = (request.getHeader("X-CompanyId") != null && !request.getHeader("X-CompanyId").isEmpty()) ? Long.valueOf(request.getHeader("X-CompanyId")) : 0;
        notificationEntity.setStakeholderCompanyId( companyId );

        if (subjectId != null) {
            notificationEntity.setBookingId(subjectId);
            notificationEntity.setType("BOOKING_FLIGHT");
        }

        notificationRP.save(notificationEntity);

    }

}
