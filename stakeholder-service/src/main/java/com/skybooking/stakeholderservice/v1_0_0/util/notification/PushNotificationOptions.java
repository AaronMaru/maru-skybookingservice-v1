package com.skybooking.stakeholderservice.v1_0_0.util.notification;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.NotificationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static com.skybooking.stakeholderservice.config.ActiveMQConfig.NOTIFICATION;

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
    private NotificationBean notificationBean;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private StakeHolderUserRP skyuserRP;


    public void sendMessageToUsers(HashMap<String, Object> dataScript) {

        String companyId = (request.getHeader("X-CompanyId") != null && !request.getHeader("X-CompanyId").isEmpty()) ? request.getHeader("X-CompanyId") : null;
        var skyuser = skyuserRP.findById((long) dataScript.get("skyuserId"));

        if (dataScript.get("companyId") != null) {
            companyId = dataScript.get("companyId").toString();
        }

        dataScript.put("companyId", companyId);
        dataScript.put("headerPlayerId", headerBean.getPlayerId());
        dataScript.put("localeId", headerBean.getLocalizationId());
        dataScript.put("fullname", skyuser.get().getLastName() + " " + skyuser.get().getFirstName());

        jmsTemplate.convertAndSend(NOTIFICATION, dataScript);

    }

    public void executeByQueue(HashMap<String, Object> dataScript) {

        try {

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            String strJsonBody = jsonToUser(con, dataScript);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            con.getResponseCode();

        } catch(Throwable t) {
            t.printStackTrace();
        }

    }


    public String jsonToUser( HttpURLConnection con, HashMap<String, Object> data ) {

        String playerId = data.get("headerPlayerId")  != null ? data.get("headerPlayerId").toString() : "";
        Long skyuserId = data.get("skyuserId") != null ? (long) Integer.parseInt(data.get("skyuserId").toString()) : null;
        Integer subjectId = data.get("subjectId") != null ? Integer.parseInt(data.get("subjectId").toString()) : null;

        if (data.containsKey("playerId")) {
            playerId = data.get("playerId").toString();
        }

        ScriptingTO scriptingTO = notificationNQ.scripting( (long) Integer.parseInt(data.get("localeId").toString()), data.get("scriptKey").toString());

        if (!data.containsKey("noInsert")) {
            Long companyId = (data.get("companyId") != null) ? (long) Integer.parseInt(data.get("companyId").toString()) : null;
            addNotificationHisitory(subjectId, scriptingTO.getScriptId(), skyuserId, companyId);
        }

        String subject = notificationBean.scriptStringReplace(scriptingTO.getSubject(), data);

        String bookingCode = "";
        String notiType = null;
        if (data.get("scriptKey").equals("user_receive_flght_ticket")) {
            notiType = "BOOKING_FLIGHT";
            bookingCode = "";
        }

        con.setRequestProperty("Authorization", environment.getProperty("spring.onesignal.apiKey"));
        return  "{"
                +   "\"app_id\": \""+ environment.getProperty("spring.onesignal.appId") +"\","
                +   "\"include_player_ids\": [\""+playerId+"\"],"
                +   "\"data\": {\"notiType\": \""+notiType+"\", \"bookingCode\": \""+bookingCode+" \"},"
                +   "\"contents\": {\"en\": \""+subject+"\"}"
                + "}";

    }

    public void addNotificationHisitory(Integer subjectId, Integer scriptId, Long skyuserId, Long companyId) {

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setSendScriptId(scriptId);
        notificationEntity.setStakeholderUserId(skyuserId);

        notificationEntity.setStakeholderCompanyId( companyId );

        if (subjectId != null) {
            notificationEntity.setBookingId(subjectId);
            notificationEntity.setType("BOOKING_FLIGHT");
        }

        notificationRP.save(notificationEntity);

    }
    
}
