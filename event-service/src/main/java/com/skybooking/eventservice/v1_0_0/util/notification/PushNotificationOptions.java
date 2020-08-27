package com.skybooking.eventservice.v1_0_0.util.notification;

import com.skybooking.eventservice.v1_0_0.client.onesignal.NotificationAction;
import com.skybooking.eventservice.v1_0_0.io.entity.notification.NotificationEntity;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.eventservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void sendMessageToUsers(NotificationDTO notificationDTO) {

        Long localeID = headerBean.getLocalizationId();

        ScriptingTO scriptingTO = notificationNQ.scripting(localeID, notificationDTO.getScript());
        List<String> playerIds = notificationNQ.getPlayerIdByStakeholderUserId(
                notificationDTO.getStakeholderUserId().intValue()
        );

        addNotificationHistory(notificationDTO, scriptingTO);

        playerIds.forEach(playerId -> {

            String notiType = notificationDTO.getType();

            var sendData = new JSONObject();

            var data = new JSONObject();
            data.put("notiType", notiType);
            data.put("bookingCode", "bookingCode");

            var contents = new JSONObject();
            contents.put("en", scriptingTO.getSubject());

            JSONArray playerIdArray = new JSONArray();
            playerIdArray.put(playerId);

            sendData.put("contents", contents);
            sendData.put("data", data);
            sendData.put("include_player_ids", playerIdArray);
            sendData.put("app_id", environment.getProperty("spring.onesignal.appId"));

            notificationAction.sendNotification(sendData.toString());
        });

    }

    public void addNotificationHistory(NotificationDTO notificationDTO, ScriptingTO scriptingTO) {

        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(notificationDTO, notificationEntity);
        notificationEntity.setSendScriptId(scriptingTO.getScriptId());
        notificationRP.save(notificationEntity);

    }

}
