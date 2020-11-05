package com.skybooking.eventservice.v1_0_0.service.notification;

import com.skybooking.eventservice.v1_0_0.io.entity.notification.NotificationEntity;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.eventservice.v1_0_0.io.nativeQuery.notification.ScriptingTO;
import com.skybooking.eventservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationTopUpRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationUpgradeLevelRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.sms.SkyPointUpgradeLevelSmsRQ;
import com.skybooking.eventservice.v1_0_0.util.auth.JwtUtils;
import com.skybooking.eventservice.v1_0_0.util.email.EmailBean;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.eventservice.v1_0_0.util.notification.NotificationDTO;
import com.skybooking.eventservice.v1_0_0.util.notification.PushNotificationOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class NotificationIP implements NotificationSV {

    @Autowired
    private PushNotificationOptions pushNotification;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private NotificationRP notificationRP;

    @Autowired
    private NotificationNQ notificationNQ;

    @Override
    public void topUp(NotificationTopUpRQ notificationTopUpRQ) {

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notificationTopUpRQ, notificationDTO);

        Long localeID = headerBean.getLocalizationId();

        ScriptingTO scriptingTO = notificationNQ.scripting(localeID, notificationDTO.getScript());
        List<String> playerIds = notificationNQ.getPlayerIdByStakeholderUserId(
                notificationDTO.getStakeholderUserId().intValue()
        );

        this.addNotificationHistory(notificationDTO, scriptingTO);

        pushNotification.sendNotificationMultiplayer(playerIds, notificationDTO, scriptingTO);

    }


    @Override
    public void sendNotification(NotificationRQ notificationRQ) {
        Long companyId = httpServletRequest.getHeader("X-CompanyId") != null &&
                !httpServletRequest.getHeader("X-CompanyId").isEmpty() ?
                Long.valueOf(httpServletRequest.getHeader("X-CompanyId")) : null;

        Long stakeholderUserId = jwtUtils.getClaim("stakeholderId", Long.class);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notificationRQ, notificationDTO);
        String notificationType = notificationRQ.getType();
        if (notificationType.equalsIgnoreCase("EARNED_POINT")) {
            notificationDTO.setScript("skp_earn_notification");
        } else if (notificationType.equalsIgnoreCase("REDEEM_POINT")) {
            notificationDTO.setScript("skp_redeem_notification");
        } else if (notificationType.equalsIgnoreCase("REFUND_POINT")) {
            notificationDTO.setScript("skp_refund_notification");
        }
        notificationDTO.setStakeholderUserId(stakeholderUserId);
        notificationDTO.setStakeholderCompanyId(companyId);

        pushNotification.sendNotificationMultiProduct(notificationDTO);

    }

    @Override
    public void upgradeLevel(NotificationUpgradeLevelRQ notificationUpgradeLevelRQ) {

        Long stakeholderUserId = jwtUtils.getClaim("stakeholderId", Long.class);

        Long companyId = httpServletRequest.getHeader("X-CompanyId") != null &&
                !httpServletRequest.getHeader("X-CompanyId").isEmpty() ?
                Long.valueOf(httpServletRequest.getHeader("X-CompanyId")) : null;

        notificationUpgradeLevelRQ.setScript("skp_upgrade_level_notification");
        notificationUpgradeLevelRQ.setStakeholderUserId(stakeholderUserId);
        notificationUpgradeLevelRQ.setStakeholderCompanyId(companyId);

        Long localeID = headerBean.getLocalizationId();

        ScriptingTO scriptingTO = notificationNQ.scripting(localeID, notificationUpgradeLevelRQ.getScript());
        List<String> playerIds = notificationNQ.getPlayerIdByStakeholderUserId(
                notificationUpgradeLevelRQ.getStakeholderUserId().intValue()
        );

        NotificationDTO notificationDTO = new NotificationDTO();

        BeanUtils.copyProperties(notificationUpgradeLevelRQ, notificationDTO);

        this.addNotificationHistory(notificationDTO, scriptingTO);

        pushNotification.sendNotificationMultiplayer(playerIds, notificationDTO, scriptingTO);

    }

    public void addNotificationHistory(NotificationDTO notificationDTO, ScriptingTO scriptingTO) {

        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(notificationDTO, notificationEntity);
        notificationEntity.setSendScriptId(scriptingTO.getScriptId());
        notificationRP.save(notificationEntity);

    }

}
