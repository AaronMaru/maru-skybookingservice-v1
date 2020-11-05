package com.skybooking.eventservice.v1_0_0.util.notification;

import com.skybooking.eventservice.constant.TransactionForConstant;
import com.skybooking.eventservice.v1_0_0.client.onesignal.NotificationAction;
import com.skybooking.eventservice.v1_0_0.io.entity.notification.NotificationEntity;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.eventservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.eventservice.v1_0_0.util.email.EmailBean;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skybooking.eventservice.constant.SmsKey.API_PRODUCT_FLIGHT;
import static com.skybooking.eventservice.constant.SmsKey.API_PRODUCT_HOTEL;

@Component
public class PushNotificationOptions {

    @Autowired
    Environment environment;

    @Autowired
    private NotificationNQ notificationNQ;

    @Autowired
    private NotificationRP notificationRP;

    @Autowired
    private NotificationAction notificationAction;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private EmailBean emailBean;

    public void sendNotificationMultiProduct(NotificationDTO notificationDTO) {

        Long localeID = headerBean.getLocalizationId();

        Map<String, Object> smsData = new HashMap<>();

        if (notificationDTO.getTransactionFor().equalsIgnoreCase(TransactionForConstant.FLIGHT)) {
            smsData = emailBean.dataSMSTemplate(API_PRODUCT_FLIGHT, smsData);
        } else {
            smsData = emailBean.dataSMSTemplate(API_PRODUCT_HOTEL, smsData);
        }

        ScriptingTO scriptingTO = notificationNQ.scripting(localeID, notificationDTO.getScript());
        List<String> playerIds = notificationNQ.getPlayerIdByStakeholderUserId(
                notificationDTO.getStakeholderUserId().intValue()
        );

        if (smsData.get("hotel") != null) {
            scriptingTO.setSubject(scriptingTO.getSubject().replace("{{TRANSACTION_FOR}}",
                    smsData.get("hotel").toString()));
        }

        if (smsData.get("flight") != null) {
            scriptingTO.setSubject(scriptingTO.getSubject().replace("{{TRANSACTION_FOR}}",
                    smsData.get("flight").toString()));
        }

        this.sendNotificationMultiplayer(playerIds, notificationDTO, scriptingTO);

    }



    public void sendNotificationMultiplayer(List<String> playerIds, NotificationDTO notificationDTO, ScriptingTO scriptingTO) {


        playerIds.forEach(playerId -> {

            String notiType = notificationDTO.getType();

            var sendData = new JSONObject();

            var data = new JSONObject();
            data.put("notiType", notiType);
            data.put("transactionCode", notificationDTO.getTransactionCode());

            var contents = new JSONObject();
            String script = scriptingTO.getSubject();

            JSONArray playerIdArray = new JSONArray();
            playerIdArray.put(playerId);

            if (script.contains("{{AMOUNT}}")) {
                script = script.replace("{{AMOUNT}}", notificationDTO.getAmount().toString());
            }

            contents.put("en", script);
            sendData.put("contents", contents);
            sendData.put("data", data);
            sendData.put("include_player_ids", playerIdArray);
            sendData.put("app_id", environment.getProperty("spring.onesignal.appId"));

            notificationAction.sendNotification(sendData.toString());
        });
    }
}
