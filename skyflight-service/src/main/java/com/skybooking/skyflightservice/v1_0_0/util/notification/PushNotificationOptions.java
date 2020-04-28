package com.skybooking.skyflightservice.v1_0_0.util.notification;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.notification.NotificationEntity;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
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

    @Autowired
    private BookingRP bookingRP;

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

        ScriptingTO scriptingTO = notificationNQ.scripting((long) 1, scriptKey);
        BookingEntity booking = bookingRP.findById(bookingId).get();
        String playerId = notificationNQ.getPlayerIdByStakeholderUserId(booking.getStakeholderUserId());

        addNotificationHistory(booking, scriptingTO, skyuserId);
        con.setRequestProperty("Authorization", environment.getProperty("spring.onesignal.apiKey"));

        return  "{"
                +   "\"app_id\": \""+ environment.getProperty("spring.onesignal.appId") +"\","
                +   "\"include_player_ids\": [\""+playerId+"\"],"
                +   "\"data\": {\"urlKey\": \""+scriptKey+"\"},"
                +   "\"contents\": {\"en\": \""+scriptingTO.getSubject()+"\"}"
                + "}";

    }

    public void addNotificationHistory(BookingEntity booking, ScriptingTO scriptingTO, Long skyuserId) {

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setSendScriptId(scriptingTO.getScriptId());
        notificationEntity.setStakeholderUserId( (skyuserId == null) ? jwtUtils.getUserToken().getStakeholderId() : skyuserId );
        notificationEntity.setStakeholderCompanyId( booking.getStakeholderCompanyId() == null ? 0 : booking.getStakeholderCompanyId().longValue() );

        if (booking.getId() != null) {
            notificationEntity.setBookingId(booking.getId());
            notificationEntity.setType("BOOKING_FLIGHT");
        }

        notificationRP.save(notificationEntity);

    }

}
