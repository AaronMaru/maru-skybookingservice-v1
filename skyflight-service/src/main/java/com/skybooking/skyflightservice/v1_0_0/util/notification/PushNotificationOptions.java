package com.skybooking.skyflightservice.v1_0_0.util.notification;

import com.skybooking.skyflightservice.v1_0_0.client.onesignal.action.NotificationAction;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.locale.LocaleEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.notification.NotificationEntity;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
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
    private JwtUtils jwtUtils;

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private BookingLanguageRedisRP bookingLanguageRedisRP;

    @Autowired
    private LocaleRP localeRP;

    @Autowired
    private NotificationAction notificationAction;

    public void sendMessageToUsers(String scriptKey, Integer bookingId, Long skyuserId) {

        BookingEntity booking = bookingRP.findById(bookingId).get();
        var bookingLanguageCached = bookingLanguageRedisRP.findById(booking.getBookingCode());

        LocaleEntity localeEntity = localeRP.findLocaleByLocale(bookingLanguageCached.get().getLanguage());

        ScriptingTO scriptingTO = notificationNQ.scripting(localeEntity.getId(), scriptKey);
        List<String> playerIds = notificationNQ.getPlayerIdByStakeholderUserId(booking.getStakeholderUserId());

        addNotificationHistory(booking, scriptingTO, skyuserId);

        playerIds.forEach(playerId -> {

            String bookingCode = booking.getBookingCode();
            String notiType = "BOOKING_FLIGHT";


            var notificationData = new JSONObject();

            var data = new JSONObject();
            data.put("notiType", notiType);
            data.put("bookingCode", bookingCode);

            var contents = new JSONObject();
            contents.put("en", scriptingTO.getSubject());

            JSONArray playerIdArray = new JSONArray();
            playerIdArray.appendElement(playerId);

            notificationData.put("contents", contents);
            notificationData.put("data", data);
            notificationData.put("include_player_ids", playerIdArray);
            notificationData.put("app_id", environment.getProperty("spring.onesignal.appId"));

            notificationAction.sendNotification(notificationData.toString());
        });
    }

    public void addNotificationHistory(BookingEntity booking, ScriptingTO scriptingTO, Long skyuserId) {

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setSendScriptId(scriptingTO.getScriptId());
        notificationEntity.setStakeholderUserId((skyuserId == null) ? jwtUtils.getUserToken().getStakeholderId() : skyuserId);
        notificationEntity.setStakeholderCompanyId(booking.getStakeholderCompanyId() == null ? null : booking.getStakeholderCompanyId().longValue());

        if (booking.getId() != null) {
            notificationEntity.setBookingId(booking.getId());
            notificationEntity.setType("BOOKING_FLIGHT");
        }

        notificationRP.save(notificationEntity);

    }

}
