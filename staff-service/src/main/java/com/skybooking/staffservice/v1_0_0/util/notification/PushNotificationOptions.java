package com.skybooking.staffservice.v1_0_0.util.notification;

import com.skybooking.staffservice.constant.NotificationConstant;
import com.skybooking.staffservice.v1_0_0.io.enitity.notification.NotificationEntity;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.staffservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.staffservice.v1_0_0.util.JwtUtils;
import com.skybooking.staffservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.skybooking.staffservice.config.ActiveMQConfig.NOTIFICATION;

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
    private JmsTemplate jmsTemplate;


    public void sendMessageToUsers(HashMap<String, Object> dataScript) {

        dataScript.put("headerPlayerId", headerBean.getPlayerId());
        dataScript.put("localeId", headerBean.getLocalizationId());
        dataScript.put("fullName", jwtUtils.getClaim("fullName", String.class));

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


    public String jsonToUser( HttpURLConnection con, HashMap<String, Object> dataScript ) {

        String playerId = dataScript.get("headerPlayerId")  != null ? dataScript.get("headerPlayerId").toString() : "";

        if (dataScript.containsKey("playerId")) {
            playerId = dataScript.get("playerId").toString();
        }

        ScriptingTO scriptingTO = notificationNQ.scripting( (long) Integer.parseInt(dataScript.get("localeId").toString()), dataScript.get("scriptKey").toString() );

        if (!dataScript.containsKey("noInsert")) {
            addNotificationHisitory(dataScript);
        }

        scriptingTO.setSubject(scriptStringReplace(scriptingTO.getSubject(), dataScript));

        String bookingCode = "";
        String notiType = null;
        if (dataScript.get("scriptKey").equals("user_receive_flght_ticket")) {
            notiType = "BOOKING_FLIGHT";
            bookingCode = "";
        }

        con.setRequestProperty("Authorization", environment.getProperty("spring.onesignal.apiKey"));
        return  "{"
                +   "\"app_id\": \""+ environment.getProperty("spring.onesignal.appId") +"\","
                +   "\"include_player_ids\": [\""+playerId+"\"],"
                +   "\"data\": {\"notiType\": \""+notiType+"\", \"bookingCode\": \""+bookingCode+" \"},"
                +   "\"contents\": {\"en\": \""+scriptingTO.getSubject()+"\"}"
                + "}";

    }

    public void addNotificationHisitory(HashMap<String, Object> dataScript) {

        Long skyuserId = dataScript.get("skyuserId") != null ? (long) Integer.parseInt(dataScript.get("skyuserId").toString()) : null;
        Integer subjectId = dataScript.get("subjectId") != null ? Integer.parseInt(dataScript.get("subjectId").toString()) : null;
        String type = dataScript.get("type") != null ? dataScript.get("type").toString() : null;

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setSendScriptId(Integer.parseInt(dataScript.get("scriptId").toString()));
        notificationEntity.setStakeholderUserId( (skyuserId == null) ? jwtUtils.getUserToken().getStakeholderId() : skyuserId );
        notificationEntity.setType(type);

        EntityReplace(notificationEntity, dataScript.get("scriptKey").toString(), dataScript);

        Long companyId = (request.getHeader("X-CompanyId") != null && !request.getHeader("X-CompanyId").isEmpty()) ? Long.valueOf(request.getHeader("X-CompanyId")) : 0;
        if (dataScript.containsKey("addSkyuser")) {
            companyId = null;
        }
        notificationEntity.setStakeholderCompanyId( companyId );

        if (subjectId != null) {
            notificationEntity.setBookingId(subjectId);
            notificationEntity.setType("BOOKING_FLIGHT");
        }

        notificationRP.save(notificationEntity);

    }

    private String scriptStringReplace(String subject, HashMap<String, Object> dataScript) {

        dataScript.put(NotificationConstant.FULL_NAME, dataScript.get("fullName"));
        dataScript.put(NotificationConstant.DATE_TIME, new Date().toString());

        for (Map.Entry<String, String> data : NotificationConstant.STRING_REPLACE.entrySet()) {
            if (subject.contains(data.getValue())) {
                subject = subject.replace(data.getValue(), dataScript.get(data.getKey()).toString());
            }
        }

        return subject;

    }

    private void EntityReplace(NotificationEntity notificationEntity, String key, HashMap<String, Object> dataScript) {
        switch (key) {
            case "skyowner_update_staff_role" :
                notificationEntity.setReplaceOne(dataScript.get(NotificationConstant.CURRENT_ROLE).toString());
                notificationEntity.setReplaceTwo(dataScript.get(NotificationConstant.NEW_ROLE).toString());
                break;
            case "skyowner_add_staffs" :
                notificationEntity.setReplaceOne(dataScript.get("inviteId") != null ? dataScript.get("inviteId").toString() : null);
            default:
                break;
        }
    }

}
